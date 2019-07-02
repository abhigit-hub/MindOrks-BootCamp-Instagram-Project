package com.footinit.instagram.data.repository.post

import androidx.lifecycle.LiveData
import com.footinit.instagram.data.local.db.entity.PostWithUser
import com.footinit.instagram.data.local.db.entity.UserEntity
import io.reactivex.Observable
import okhttp3.MultipartBody

interface PostRepository {

    fun getAllMyPosts(): LiveData<List<PostWithUser>>

    fun getAllPosts(): LiveData<List<PostWithUser>>

    fun doFetchAndSaveAllMyPosts(): Observable<List<Long>>

    fun doFetchAndSaveAllPosts(firstPostId: String?, lastPostId: String?): Observable<Pair<String, String>>

    fun getLikesByPost(postId: String): LiveData<MutableList<UserEntity>>

    fun doLikePost(postId: String): Observable<Long>

    fun doUnlikePost(postId: String): Observable<Unit>

    fun doCreatePost(image: MultipartBody.Part, imageResolution: Pair<Int, Int>): Observable<Long>
}