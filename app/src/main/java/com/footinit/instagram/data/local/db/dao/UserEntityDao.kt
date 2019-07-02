package com.footinit.instagram.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.footinit.instagram.data.local.db.entity.UserEntity

@Dao
interface UserEntityDao {
    @Insert
    fun insert(userEntity: UserEntity): Long

    @Insert
    fun insert(userEntityList: List<UserEntity>): List<Long>

    @Update
    fun updateProfile(userEntity: UserEntity): Int

    @Query("SELECT * FROM user_entity WHERE postId = :postId")
    fun getUsers(postId: String): LiveData<MutableList<UserEntity>>

    @Query("DELETE FROM user_entity WHERE postId = :postId AND userId = :userId")
    fun delete(postId: String, userId: String)

    @Delete
    fun delete(userEntity: UserEntity)

    @Query("DELETE FROM user_entity")
    fun nukeTable(): Int
}