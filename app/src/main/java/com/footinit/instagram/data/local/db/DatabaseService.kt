package com.footinit.instagram.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.footinit.instagram.data.local.db.dao.*
import com.footinit.instagram.data.local.db.entity.DummyEntity
import com.footinit.instagram.data.local.db.entity.Post
import com.footinit.instagram.data.local.db.entity.Profile
import com.footinit.instagram.data.local.db.entity.UserEntity
import com.footinit.instagram.utils.common.TimeStampConverter
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        DummyEntity::class,
        Profile::class,
        Post::class,
        UserEntity::class
    ],
    exportSchema = false,
    version = 1
)
@TypeConverters(TimeStampConverter::class)
abstract class DatabaseService : RoomDatabase() {

    abstract fun dummyDao(): DummyDao

    abstract fun profileInfoDao(): ProfileDao

    abstract fun postDao(): PostDao

    abstract fun userEntityDao(): UserEntityDao

    abstract fun postWithUserDao(): PostWithUserDao
}