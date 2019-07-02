package com.footinit.instagram.data.remote

import com.footinit.instagram.data.remote.di.AccessTokenInfo
import com.footinit.instagram.data.remote.di.ApiKeyInfo
import com.footinit.instagram.data.remote.di.UserInfo
import com.footinit.instagram.utils.common.ResultFetcher
import javax.inject.Inject

class RequestHeaders @Inject constructor(
    @ApiKeyInfo val apiKey: String,
    @UserInfo val userIdFetcher: ResultFetcher<String>,
    @AccessTokenInfo val accessTokenFetcher: ResultFetcher<String>
) {
    object Key {
        const val API_AUTH_TYPE = "API_AUTH_TYPE"
        const val AUTH_PUBLIC = "$API_AUTH_TYPE: public"
        const val AUTH_PROTECTED = "$API_AUTH_TYPE: protected"
    }

    enum class Type constructor(val value: String) {
        PUBLIC("public"),
        PROTECTED("protected")
    }

    enum class Param(val value: String) {
        API_KEY("x-api-key"),
        USER_ID("x-user-id"),
        ACCESS_TOKEN("x-access-token")
    }
}