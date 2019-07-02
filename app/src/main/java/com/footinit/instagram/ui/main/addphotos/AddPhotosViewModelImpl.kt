package com.footinit.instagram.ui.main.addphotos

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.footinit.instagram.R
import com.footinit.instagram.data.repository.post.PostRepository
import com.footinit.instagram.ui.base.BaseViewModelImpl
import com.footinit.instagram.utils.common.Event
import com.footinit.instagram.utils.common.FileUtils
import com.footinit.instagram.utils.common.LoadMoreListener
import com.footinit.instagram.utils.common.Resource
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MultipartBody
import java.util.concurrent.atomic.AtomicBoolean

class AddPhotosViewModelImpl(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val postRepository: PostRepository
) : BaseViewModelImpl(schedulerProvider, compositeDisposable, networkHelper), AddPhotosViewModel {

    private var lastFetchedIndex = 0
    private var galleryImageCount = 0
    private var isFetchingImageList = AtomicBoolean(false)
    private var imagePathList: MutableList<String> = mutableListOf()
    private val imagePathListLiveData: MutableLiveData<MutableList<String>> = MutableLiveData()
    private val backNavigation: MutableLiveData<Event<Bundle>> = MutableLiveData()
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    override fun getImagePathList(): LiveData<MutableList<String>> = imagePathListLiveData
    override fun getBackNavigation(): LiveData<Event<Bundle>> = backNavigation
    override fun getIsLoading(): LiveData<Boolean> = isLoading
    override fun getLoadMoreListener() = object : LoadMoreListener {
        override fun onLoadMore() {
            if (lastFetchedIndex == galleryImageCount - 1) {
                //Do Nothing
            } else if (!isFetchingImageList.getAndSet(true)) {
                readImagesFromGallery()
            }
        }
    }

    init {
        compositeDisposable.addAll(
            getGalleryImageCountAsObservable()
                .subscribeOn(schedulerProvider.io())
                .subscribe({
                    it?.run { galleryImageCount = this }
                }, {
                    messageLiveData.postValue(Resource.error(it?.message))
                })
        )
    }

    override fun onBackNavigation() = backNavigation.postValue(Event(Bundle()))

    override fun onViewCreated() {}

    override fun readImagesFromGallery() {
        compositeDisposable.addAll(
            fetchImagesFromGalleryAsObservable()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(
                    {
                        it?.let { newList ->
                            if (newList.isNotEmpty()) {
                                imagePathListLiveData.value = newList
                                imagePathList.addAll(newList)
                                lastFetchedIndex = imagePathList.size - 1
                            }
                            isFetchingImageList.set(false)
                        }
                    },
                    {
                        messageLiveData.postValue(Resource.error(it?.message))
                    }
                )
        )
    }

    override fun onCreatePost(image: MultipartBody.Part, imageResolution: Pair<Int, Int>) {
        if (checkInternetConnectionWithMessage()) {
            isLoading.postValue(true)
            compositeDisposable.addAll(
                postRepository.doCreatePost(image, imageResolution)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(
                        {
                            isLoading.postValue(false)
                            if (it > 0)
                                messageStringIdLiveData.postValue(Resource.success(R.string.main_addphoto_post_created))
                        },
                        {
                            handleNetworkError(it)
                            isLoading.postValue(false)
                        }
                    )
            )
        }
    }

    private fun fetchImagesFromGalleryAsObservable(): Observable<MutableList<String>> =
        Observable.fromCallable { FileUtils.fetchImagesFromGallery(lastFetchedIndex) }

    private fun getGalleryImageCountAsObservable(): Observable<Int> =
        Observable.fromCallable { FileUtils.getGalleryImageCount() }
}