package com.footinit.instagram.data.remote.response

import com.footinit.instagram.data.local.db.entity.Profile
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FetchMyInfoResponse(
    @Expose
    @SerializedName("statusCode")
    val statusCode: String,

    @Expose
    @SerializedName("status")
    val status: Int,

    @Expose
    @SerializedName("message")
    val message: String,

    @Expose
    @SerializedName("data")
    val data: Profile
)
