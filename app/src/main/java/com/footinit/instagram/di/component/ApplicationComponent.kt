package com.footinit.instagram.di.component

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.footinit.instagram.InstagramApplication
import com.footinit.instagram.data.local.db.DatabaseService
import com.footinit.instagram.data.local.db.di.DatabaseModule
import com.footinit.instagram.data.local.prefs.di.PreferenceModule
import com.footinit.instagram.data.local.prefs.user.UserPreferences
import com.footinit.instagram.data.remote.NetworkService
import com.footinit.instagram.data.remote.RequestHeaders
import com.footinit.instagram.data.remote.di.NetworkModule
import com.footinit.instagram.data.repository.di.RepositoryModule
import com.footinit.instagram.di.ApplicationContext
import com.footinit.instagram.di.module.ApplicationModule
import com.footinit.instagram.utils.network.NetworkHelper
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        PreferenceModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        RepositoryModule::class
    ]
)
interface ApplicationComponent {

    fun inject(app: InstagramApplication)

    @ApplicationContext
    fun getContext(): Context

    fun getApplication(): Application

    fun getNetworkService(): NetworkService

    fun getDatabaseService(): DatabaseService

    fun getUserPreferences(): UserPreferences

    fun getSharedPreferences(): SharedPreferences

    fun getNetworkHelper(): NetworkHelper

    fun getRequestHeaders(): RequestHeaders
}