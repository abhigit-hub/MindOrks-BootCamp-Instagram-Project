package com.footinit.instagram.data.remote.di

import com.footinit.instagram.data.remote.NetworkService
import com.footinit.instagram.data.remote.Networking
import com.footinit.instagram.data.remote.interceptor.RefreshTokenInterceptor
import com.footinit.instagram.data.remote.interceptor.RequestHeaderInterceptor
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkService(
        @NetworkCacheDirectory cacheDir: File,
        @BaseUrl baseUrl: String,
        @NetworkCacheSize cacheSize: Long,
        requestHeaderInterceptor: RequestHeaderInterceptor,
        refreshTokenInterceptor: RefreshTokenInterceptor
    ): NetworkService =
        Networking.create(
            baseUrl,
            cacheDir,
            cacheSize,
            requestHeaderInterceptor,
            refreshTokenInterceptor
        )
}