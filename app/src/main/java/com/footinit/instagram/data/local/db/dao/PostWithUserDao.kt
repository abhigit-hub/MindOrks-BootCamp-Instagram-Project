package com.footinit.instagram.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.footinit.instagram.data.local.db.entity.PostWithUser

@Dao
interface PostWithUserDao {

    @Query("SELECT * FROM posts WHERE isMyPost = 1 ORDER BY createdAt DESC")
    @Transaction
    fun getAllMyPosts(): LiveData<List<PostWithUser>>

    @Query("SELECT * FROM posts WHERE isMyPost = 0")
    @Transaction
    fun getAllPosts(): LiveData<List<PostWithUser>>
}