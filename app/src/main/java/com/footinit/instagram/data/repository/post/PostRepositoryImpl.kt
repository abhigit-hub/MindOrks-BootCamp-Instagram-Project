package com.footinit.instagram.data.repository.post

import androidx.lifecycle.LiveData
import com.facebook.stetho.server.http.HttpStatus
import com.footinit.instagram.data.local.db.DatabaseService
import com.footinit.instagram.data.local.db.entity.Post
import com.footinit.instagram.data.local.db.entity.PostWithUser
import com.footinit.instagram.data.local.db.entity.UserEntity
import com.footinit.instagram.data.local.prefs.user.UserPreferences
import com.footinit.instagram.data.model.User
import com.footinit.instagram.data.remote.NetworkService
import com.footinit.instagram.data.remote.request.CreatePostRequest
import com.footinit.instagram.data.remote.request.LikeRequest
import com.footinit.instagram.utils.log.Logger
import io.reactivex.Observable
import okhttp3.MultipartBody
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    private val userPreferences: UserPreferences
) : PostRepository {

    companion object {
        val TAG = PostRepositoryImpl::class.java.simpleName
    }

    override fun getAllMyPosts(): LiveData<List<PostWithUser>> =
        databaseService.postWithUserDao().getAllMyPosts()

    override fun getAllPosts(): LiveData<List<PostWithUser>> =
        databaseService.postWithUserDao().getAllPosts()

    override fun doFetchAndSaveAllMyPosts(): Observable<List<Long>> =
        networkService.doFetchMyPostsCall()
            .flatMapObservable { response ->
                deleteMyStalePostsAsObservable()
                    .ignoreElements().andThen(Observable.fromIterable(response.data))
            }
            .concatMap { post -> networkService.doGetPostDetails(post.id).toObservable() }
            .concatMap { response ->
                response.data.isMyPost = true
                Observable.just(response.data)
            }
            .toList()
            .flatMapObservable { posts ->
                insertPostsAsObservable(posts)
                    .ignoreElements().andThen(Observable.just(posts))
            }
            .flatMap { posts -> Observable.fromCallable { formatAndExtractUserListFromPosts(posts) } }
            .flatMap { users -> insertPostLikesAsObservable(users) }

    override fun doFetchAndSaveAllPosts(firstPostId: String?, lastPostId: String?): Observable<Pair<String, String>> {
        return networkService.doFetchAllPostsApiCall(firstPostId, lastPostId)
            .flatMapObservable { response ->
                if (firstPostId != null && lastPostId != null)
                    Observable.fromIterable(response.data)
                else deleteAllStalePostsAsObservable()
                    .ignoreElements().andThen(Observable.fromIterable(response.data))
            }
            .filter { post -> post.user?.id != userPreferences.getUserId() }.toList()
            .flatMapObservable { posts ->
                insertPostsAsObservable(posts)
                    .ignoreElements().andThen(Observable.just(posts))
            }
            .flatMap { posts ->
                Observable.fromCallable {
                    val users = formatAndExtractUserListFromPosts(posts)
                    Pair(users, Pair(posts.first().id, posts.last().id))
                }
            }
            .flatMap { pair ->
                insertPostLikesAsObservable(pair.first)
                    .ignoreElements().andThen(Observable.just(pair.second))
            }
    }

    override fun getLikesByPost(postId: String): LiveData<MutableList<UserEntity>> =
        databaseService.userEntityDao().getUsers(postId)

    override fun doLikePost(postId: String): Observable<Long> =
        networkService.doLikePost(LikeRequest(postId))
            .doOnSuccess { Logger.d(TAG, it.message) }
            .doOnError { Logger.d(TAG, it.message.toString()) }
            .filter { response -> response.status == HttpStatus.HTTP_OK }
            .flatMapObservable { response ->
                val userEntity = UserEntity(
                    0, userPreferences.getUserId()!!, postId,
                    userPreferences.getUserName()!!, userPreferences.getUserProfilePicUrl()
                )
                insertPostLikeAsObservable(userEntity)
            }

    override fun doUnlikePost(postId: String): Observable<Unit> =
        networkService.doUnlikePost(LikeRequest(postId))
            .filter { response -> response.status == HttpStatus.HTTP_OK }
            .flatMapObservable { response -> deletePostLikeAsObservable(postId, userPreferences.getUserId()!!) }

    override fun doCreatePost(image: MultipartBody.Part, imageResolution: Pair<Int, Int>): Observable<Long> =
        networkService.doImageUpload(image)
            .doOnError { Logger.d(TAG, it.message.toString()) }
            .filter { imageUploadResponse -> imageUploadResponse.status == HttpStatus.HTTP_OK }
            .flatMapSingle { imageUploadResponse ->
                networkService.doCreatePost(
                    CreatePostRequest(
                        imageUploadResponse.data.imageUrl,
                        imageResolution.first,
                        imageResolution.second
                    )
                )
            }
            .filter { createPostResponse -> createPostResponse.status == HttpStatus.HTTP_OK }
            .flatMapObservable { createPostResponse ->
                val user = User(
                    userPreferences.getUserId()!!,
                    userPreferences.getUserName()!!,
                    userPreferences.getUserProfilePicUrl()
                )
                val post = Post(
                    0,
                    createPostResponse.data.id,
                    createPostResponse.data.imgUrl,
                    createPostResponse.data.imgWidth,
                    createPostResponse.data.imgHeight,
                    user,
                    null,
                    createPostResponse.data.createdAt,
                    true
                )

                insertPostAsObservable(post)
            }

    private fun formatAndExtractUserListFromPosts(posts: List<Post>): List<UserEntity> {
        val userList = mutableListOf<UserEntity>()

        posts.iterator().forEach { post ->
            post.likedBy?.iterator()?.forEach {
                it.postId = post.id
                userList.add(it)
            }
        }
        return userList
    }

    private fun insertPostsAsObservable(posts: List<Post>): Observable<List<Long>> =
        Observable.fromCallable { databaseService.postDao().insertPosts(posts) }

    private fun insertPostAsObservable(post: Post): Observable<Long> =
        Observable.fromCallable { databaseService.postDao().insertPost(post) }

    private fun insertPostLikesAsObservable(users: List<UserEntity>): Observable<List<Long>> =
        Observable.fromCallable { databaseService.userEntityDao().insert(users) }

    private fun insertPostLikeAsObservable(userEntity: UserEntity): Observable<Long> =
        Observable.fromCallable { databaseService.userEntityDao().insert(userEntity) }

    private fun deletePostLikeAsObservable(postId: String, userId: String) =
        Observable.fromCallable { databaseService.userEntityDao().delete(postId, userId) }

    private fun deleteAllStalePostsAsObservable(): Observable<Any> =
        Observable.fromCallable { databaseService.postDao().deleteAllStalePosts() }

    private fun deleteMyStalePostsAsObservable(): Observable<Any> =
        Observable.fromCallable { databaseService.postDao().deleteMyStalePosts() }
}