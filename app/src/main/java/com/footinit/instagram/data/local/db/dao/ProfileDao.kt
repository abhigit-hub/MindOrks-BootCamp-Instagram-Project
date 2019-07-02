package com.footinit.instagram.data.local.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.footinit.instagram.data.local.db.entity.Profile

@Dao
interface ProfileDao {

    @Insert
    fun insert(profile: Profile): Long

    @Update
    fun updateProfile(profile: Profile): Int

    @Query("SELECT * from profile")
    fun getProfileInfo(): LiveData<Profile>

    @Delete
    fun delete(profile: Profile)

    @Query("DELETE FROM profile")
    fun nukeTable(): Int
}