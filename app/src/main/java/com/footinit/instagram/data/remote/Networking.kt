package com.footinit.instagram.data.remote

import com.footinit.instagram.BuildConfig
import com.footinit.instagram.data.remote.interceptor.RefreshTokenInterceptor
import com.footinit.instagram.data.remote.interceptor.RequestHeaderInterceptor
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object Networking {

    private const val TAG = "Networking"
    private const val NETWORK_CALL_TIMEOUT = 60

    fun create(
        baseUrl: String,
        cacheDir: File,
        cacheSize: Long,
        headerInterceptor: RequestHeaderInterceptor,
        refreshTokenInterceptor: RefreshTokenInterceptor
    ): NetworkService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient.Builder()
                    .cache(Cache(cacheDir, cacheSize))
                    .addInterceptor(headerInterceptor)
                    .addInterceptor(refreshTokenInterceptor)
                    .addInterceptor(HttpLoggingInterceptor()
                        .apply {
                            level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                            else HttpLoggingInterceptor.Level.NONE
                        })
                    .readTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(NETWORK_CALL_TIMEOUT.toLong(), TimeUnit.SECONDS)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(NetworkService::class.java)
            .also {
                refreshTokenInterceptor.networkService = it
            }
}