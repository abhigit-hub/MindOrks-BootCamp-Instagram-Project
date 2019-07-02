package com.footinit.instagram.data.remote.interceptor

import com.footinit.instagram.data.remote.RequestHeaders
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RequestHeaderInterceptor @Inject constructor(private val requestHeaders: RequestHeaders) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        var apiAuthType = request.header(RequestHeaders.Key.API_AUTH_TYPE)
        if (apiAuthType == null) {
            apiAuthType = RequestHeaders.Type.PROTECTED.value
        }

        builder.removeHeader(RequestHeaders.Key.API_AUTH_TYPE)

        when (apiAuthType) {
            RequestHeaders.Type.PROTECTED.value -> {
                val userId = requestHeaders.userIdFetcher.fetch()
                val accessToken = requestHeaders.accessTokenFetcher.fetch()
                if (userId != null && accessToken != null) {
                    builder.addHeader(RequestHeaders.Param.USER_ID.value, userId)
                    builder.addHeader(RequestHeaders.Param.ACCESS_TOKEN.value, accessToken)
                }
                builder.addHeader(RequestHeaders.Param.API_KEY.value, requestHeaders.apiKey)
            }
            RequestHeaders.Type.PUBLIC.value -> builder.addHeader(
                RequestHeaders.Param.API_KEY.value,
                requestHeaders.apiKey
            )
            else -> builder.addHeader(RequestHeaders.Param.API_KEY.value, requestHeaders.apiKey)
        }

        return chain.proceed(builder.build())
    }
}