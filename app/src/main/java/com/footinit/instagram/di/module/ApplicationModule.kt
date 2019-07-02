package com.footinit.instagram.di.module

import android.app.Application
import android.content.Context
import com.footinit.instagram.BuildConfig
import com.footinit.instagram.InstagramApplication
import com.footinit.instagram.data.local.db.di.DatabaseInfo
import com.footinit.instagram.data.local.prefs.di.PrefsInfo
import com.footinit.instagram.data.local.prefs.user.UserPreferences
import com.footinit.instagram.data.remote.di.*
import com.footinit.instagram.di.ApplicationContext
import com.footinit.instagram.utils.common.ResultCallback
import com.footinit.instagram.utils.common.ResultFetcher
import com.footinit.instagram.utils.rx.RxSchedulerProvider
import com.footinit.instagram.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import java.io.File
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: InstagramApplication) {

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    @Singleton
    @ApiKeyInfo
    fun provideApiKey(): String = BuildConfig.API_KEY

    @Provides
    @Singleton
    @BaseUrl
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    @UserInfo
    fun provideUserId(userPreferences: UserPreferences): ResultFetcher<String> =
        object : ResultFetcher<String> {
            override fun fetch(): String? = userPreferences.getUserId()
        }

    @Provides
    @Singleton
    @AccessTokenInfo
    fun provideAccessToken(userPreferences: UserPreferences): ResultFetcher<String> =
        object : ResultFetcher<String> {
            override fun fetch(): String? = userPreferences.getAccessToken()
        }

    @Provides
    @Singleton
    @RefreshTokenInfo
    fun provideRefreshToken(userPreferences: UserPreferences): ResultFetcher<String> =
        object : ResultFetcher<String> {
            override fun fetch(): String? = userPreferences.getRefreshToken()
        }

    @Provides
    @Singleton
    @AccessTokenInfo
    fun provideAccessTokenSaveLambda(userPreferences: UserPreferences): ResultCallback<String> =
        object : ResultCallback<String> {
            override fun onResult(result: String) = userPreferences.setAccessToken(result)
        }

    @Provides
    @Singleton
    @RefreshTokenInfo
    fun provideRefreshTokenSaveLambda(userPreferences: UserPreferences): ResultCallback<String> =
        object : ResultCallback<String> {
            override fun onResult(result: String) = userPreferences.setRefreshToken(result)
        }

    @Provides
    @Singleton
    @DatabaseInfo
    fun provideDatabaseName(): String = "bootcamp-instagram-project-db"

    @Provides
    @Singleton
    @PrefsInfo
    fun providePreferenceName(): String = "bootcamp-instagram-project-prefs"

    @Provides
    @Singleton
    @NetworkCacheDirectory
    fun provideNetworkCacheDirectory(): File = application.cacheDir

    @Provides
    @Singleton
    @NetworkCacheSize
    fun provideNetworkCacheSize(): Long = 10 * 1024 * 1024 // 10MB
}