package com.footinit.instagram.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.footinit.instagram.data.local.db.entity.Post

@Dao
interface PostDao {

    @Insert
    fun insertPost(post: Post): Long

    @Insert
    fun insertPosts(posts: List<Post>): List<Long>

    @Query("DELETE FROM posts where isMyPost = '0'")
    fun deleteAllStalePosts()

    @Query("DELETE FROM posts where isMyPost = '1'")
    fun deleteMyStalePosts()
}