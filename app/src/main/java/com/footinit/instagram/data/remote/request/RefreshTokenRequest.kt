package com.footinit.instagram.data.remote.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @Expose
    @SerializedName("refreshToken")
    var refreshToken: String
)