package com.footinit.instagram.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(
    @Expose
    @SerializedName("statusCode")
    var statusCode: String,
    @Expose
    @SerializedName("message")
    var message: String? = null,

    @Expose
    @SerializedName("accessToken")
    var accessToken: String,

    @Expose
    @SerializedName("refreshToken")
    var refreshToken: String
)