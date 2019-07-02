package com.footinit.instagram.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserSession(

    @Expose
    @SerializedName("userId")
    val id: String = "",

    @Expose
    @SerializedName("userName")
    val name: String = "",

    @Expose
    @SerializedName("userEmail")
    val email: String = "",

    @Expose
    @SerializedName("accessToken")
    val accessToken: String = "",

    @Expose
    @SerializedName("refreshToken")
    val refreshToken: String = "",

    val profilePicUrl: String? = null
)