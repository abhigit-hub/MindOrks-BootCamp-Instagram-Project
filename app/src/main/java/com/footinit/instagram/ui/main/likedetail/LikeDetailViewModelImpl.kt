package com.footinit.instagram.ui.main.likedetail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.footinit.instagram.data.local.db.entity.UserEntity
import com.footinit.instagram.data.repository.post.PostRepository
import com.footinit.instagram.ui.base.BaseViewModelImpl
import com.footinit.instagram.utils.common.Event
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class LikeDetailViewModelImpl(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    postRepository: PostRepository
) : BaseViewModelImpl(schedulerProvider, compositeDisposable, networkHelper), LikeDetailViewModel {

    private var postId: MutableLiveData<String> = MutableLiveData()
    private var likeCount: LiveData<Int>
        get() = Transformations.map(users) { it?.size }
        set(value) = TODO()
    private var users: LiveData<MutableList<UserEntity>> = Transformations.switchMap(postId) {
        it?.let { postId -> postRepository.getLikesByPost(postId) }
    }
    private val backNavigation: MutableLiveData<Event<Bundle>> = MutableLiveData()

    override fun getUsers(): LiveData<MutableList<UserEntity>> = users
    override fun getUserLikeCount(): LiveData<Int> = likeCount
    override fun getBackNavigation(): LiveData<Event<Bundle>> = backNavigation

    override fun onBackNavigation() = backNavigation.postValue(Event(Bundle()))

    override fun onViewCreated() {}
    override fun updatePostId(postId: String) = this.postId.postValue(postId)
}