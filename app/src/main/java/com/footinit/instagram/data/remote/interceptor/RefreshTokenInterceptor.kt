package com.footinit.instagram.data.remote.interceptor

import com.footinit.instagram.data.remote.NetworkService
import com.footinit.instagram.data.remote.RequestHeaders
import com.footinit.instagram.data.remote.di.AccessTokenInfo
import com.footinit.instagram.data.remote.di.RefreshTokenInfo
import com.footinit.instagram.data.remote.request.RefreshTokenRequest
import com.footinit.instagram.utils.common.ResultCallback
import com.footinit.instagram.utils.common.ResultFetcher
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RefreshTokenInterceptor @Inject constructor(
    @AccessTokenInfo val accessTokenFetcher: ResultFetcher<String>,
    @AccessTokenInfo private val accessTokenCallback: ResultCallback<String>,
    @RefreshTokenInfo private val refreshTokenFetcher: ResultFetcher<String>,
    @RefreshTokenInfo private val refreshTokenCallback: ResultCallback<String>
) : Interceptor {

    companion object {
        private const val INSTRUCTION = "instruction"
        private const val REFRESH_ACCESS_TOKEN = "refresh_token"
    }

    internal lateinit var networkService: NetworkService

    //TODO: test before use
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(chain.request())

        if (!response.isSuccessful && response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            val instruction = response.header(INSTRUCTION)
            val refreshToken = refreshTokenFetcher.fetch()

            if (instruction != null && instruction == REFRESH_ACCESS_TOKEN && refreshToken != null) {
                synchronized(this) {
                    if (refreshToken == refreshTokenFetcher.fetch()) {
                        networkService
                            .doRefreshTokenCall(RefreshTokenRequest(refreshToken))
                            .execute()?.run {
                                if (isSuccessful) {
                                    body()?.let {
                                        accessTokenCallback.onResult(it.accessToken)
                                        refreshTokenCallback.onResult(it.refreshToken)
                                    }
                                }
                            }
                    }

                    accessTokenFetcher.fetch()?.let {
                        val builder = request.newBuilder()
                        builder.header(RequestHeaders.Param.ACCESS_TOKEN.value, it)
                        return chain.proceed(builder.build())
                    }
                }
            }
        }
        return response
    }
}