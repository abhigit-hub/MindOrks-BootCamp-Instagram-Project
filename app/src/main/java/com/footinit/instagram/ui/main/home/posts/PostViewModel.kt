package com.footinit.instagram.ui.main.home.posts

import androidx.databinding.ObservableField
import com.footinit.instagram.data.local.db.entity.PostWithUser
import com.footinit.instagram.data.local.db.entity.UserEntity
import com.footinit.instagram.data.model.UserSession
import com.footinit.instagram.data.remote.RequestHeaders
import com.footinit.instagram.data.repository.post.PostRepository
import com.footinit.instagram.data.repository.user.UserRepository
import com.footinit.instagram.ui.base.BaseItemViewModel
import com.footinit.instagram.ui.di.ViewModelScope
import com.footinit.instagram.utils.common.TimeUtils
import com.footinit.instagram.utils.display.DisplayUtils
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ViewModelScope
class PostViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val postRepository: PostRepository,
    userRepository: UserRepository,
    requestHeaders: RequestHeaders
) : BaseItemViewModel<PostWithUser>(networkHelper) {

    lateinit var onLikeCountClickEvent: (String) -> Unit

    private var currentPost: PostWithUser? = null
    private val currentUserSession: UserSession? = userRepository.getCurrentUserSession()


    val isLiked: ObservableField<Boolean> = ObservableField()
    val likeCount: ObservableField<Int> = ObservableField()
    private val screenHeight = DisplayUtils.getScreenHeight()
    private val screenWidth = DisplayUtils.getScreenWidth()
    private val headers = mapOf(
        Pair(RequestHeaders.Param.API_KEY.value, requestHeaders.apiKey),
        Pair(RequestHeaders.Param.USER_ID.value, currentUserSession!!.id),
        Pair(RequestHeaders.Param.ACCESS_TOKEN.value, currentUserSession.accessToken)
    )


    fun getUserName(): String = currentPost?.post?.user?.name ?: ""
    fun getProfilePicUrl(): String? = currentPost?.post?.user?.profilePicUrl
    fun getPostUrl(): String? = currentPost?.post?.imgUrl
    fun getHeaders(): Map<String, String> = headers
    fun getPlaceholderWidth(): Int = screenWidth
    fun getPlaceholderHeight(): Int = currentPost?.post?.run {
        return@run imgHeight?.let { return@let (calculateScaleFactor() * it).toInt() }
    } ?: screenHeight / 3

    fun getTimeElapsedSincePost(): String =
        currentPost?.post?.createdAt?.let { date -> TimeUtils.getTimeAgo(date) } ?: ""

    private fun getIsLiked(): Boolean = currentPost?.let { postWithUser ->
        currentUserSession?.id?.let { currentUserId ->
            postWithUser.likedBy
                ?.filter { userEntity -> userEntity.id == currentUserId }
                ?.count()?.let { it > 0 }
        }
    } ?: false

    private fun getLikeCount(): Int = currentPost?.likedBy?.count() ?: 0

    override fun setUp() {
        currentPost = data
        isLiked.set(getIsLiked())
        likeCount.set(getLikeCount())
    }

    private fun calculateScaleFactor() = currentPost?.post?.run {
        imgWidth?.let { screenWidth.toFloat() / it }
    } ?: 1f

    fun onLikeCountClicked() {
        currentPost?.post?.id?.let {
            if (it.isNotEmpty())
                onLikeCountClickEvent(it)
        }
    }

    fun onPostLikeToggled() {
        when (getIsLiked()) {
            false -> {
                showLiked()
                currentPost?.post?.let { doLikePost(it.id) }
            }
            true -> {
                showUnliked()
                currentPost?.post?.let { doUnlikePost(it.id) }
            }
        }
    }

    private fun showLiked() {
        val userEntity =
            currentPost?.post?.id?.let { postId ->
                currentUserSession?.let { userSession ->
                    UserEntity(
                        0,
                        userSession.id,
                        postId,
                        userSession.name,
                        userSession.profilePicUrl
                    )
                }
            }
        userEntity?.let { currentPost?.likedBy?.add(it) }
        isLiked.set(getIsLiked())
        likeCount.set(getLikeCount())
    }

    private fun showUnliked() {
        var removeUser: UserEntity? = null
        currentPost?.likedBy?.map { user ->
            if (user.id == currentUserSession?.id) {
                removeUser = user
            }
        }

        currentPost?.likedBy?.remove(removeUser?.let { it })
        isLiked.set(getIsLiked())
        likeCount.set(getLikeCount())
    }

    private fun doLikePost(postId: String) {
        if (checkInternetConnectionWithMessage())
            compositeDisposable.add(
                postRepository.doLikePost(postId)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({

                    }, {
                        handleNetworkError(it)
                        showUnliked()
                    })
            )
        else showUnliked()
    }

    private fun doUnlikePost(postId: String) {
        if (checkInternetConnectionWithMessage())
            compositeDisposable.addAll(
                postRepository.doUnlikePost(postId)
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({

                    }, {
                        handleNetworkError(it)
                        showLiked()
                    })
            )
        else showLiked()
    }
}