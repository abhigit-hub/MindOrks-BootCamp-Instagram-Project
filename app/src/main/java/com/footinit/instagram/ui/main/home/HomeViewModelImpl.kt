package com.footinit.instagram.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.footinit.instagram.data.local.db.entity.PostWithUser
import com.footinit.instagram.data.repository.post.PostRepository
import com.footinit.instagram.ui.base.BaseViewModelImpl
import com.footinit.instagram.utils.common.LoadMoreListener
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor

class HomeViewModelImpl(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    val postRepository: PostRepository
) : BaseViewModelImpl(schedulerProvider, compositeDisposable, networkHelper), HomeViewModel {

    private val paginator = PublishProcessor.create<Pair<String?, String?>>()

    private val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val posts: LiveData<List<PostWithUser>> = postRepository.getAllPosts()
    private var firstPostId: String? = null
    private var lastPostId: String? = null

    override fun getIsLoading(): LiveData<Boolean> = isLoading
    override fun getAllPosts(): LiveData<List<PostWithUser>> = posts
    override fun getLoadMoreListener() = object : LoadMoreListener {
        override fun onLoadMore() {
            if (isLoading.value !== null && isLoading.value == false) loadMorePosts()
        }
    }

    init {
        compositeDisposable.addAll(
            paginator
                .onBackpressureDrop()
                .doOnNext { isLoading.postValue(true) }
                .concatMap {
                    postRepository.doFetchAndSaveAllPosts(it.first, it.second)
                        .toFlowable(BackpressureStrategy.LATEST)
                        .subscribeOn(schedulerProvider.io())
                        .doOnError { handleNetworkError(it) }
                }
                .subscribe(
                    { pair ->
                        if (firstPostId.isNullOrBlank()) {
                            pair?.first.let { first -> if (!first.isNullOrBlank()) firstPostId = first }
                        }
                        pair?.second.let { second -> if (!second.isNullOrBlank()) lastPostId = second }
                        isLoading.postValue(false)
                    },
                    {
                        handleNetworkError(it)
                        isLoading.postValue(false)
                    }
                )
        )
    }

    private fun loadMorePosts() {
        if (checkInternetConnectionWithMessage()) paginator.onNext(Pair(firstPostId, lastPostId))
    }

    override fun onViewCreated() = loadMorePosts()
}