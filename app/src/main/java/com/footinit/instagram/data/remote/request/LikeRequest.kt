package com.footinit.instagram.data.remote.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LikeRequest(
    @Expose
    @SerializedName("postId")
    val postId: String
)