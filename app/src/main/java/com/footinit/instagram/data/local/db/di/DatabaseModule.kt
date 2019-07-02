package com.footinit.instagram.data.local.db.di

import android.content.Context
import androidx.room.Room
import com.footinit.instagram.data.local.db.DatabaseService
import com.footinit.instagram.di.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabaseService(
        @ApplicationContext context: Context,
        @DatabaseInfo databaseName: String
    ): DatabaseService =
        Room.databaseBuilder(context, DatabaseService::class.java, databaseName).build()

}