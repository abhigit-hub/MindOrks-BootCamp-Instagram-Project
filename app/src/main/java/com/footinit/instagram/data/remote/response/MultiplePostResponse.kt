package com.footinit.instagram.data.remote.response

import com.footinit.instagram.data.local.db.entity.Post
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MultiplePostResponse(

    @Expose
    @SerializedName("statusCode")
    var statusCode: String,

    @Expose
    @SerializedName("status")
    var status: Int,

    @Expose
    @SerializedName("message")
    var message: String,

    @Expose
    @SerializedName("data")
    var data: List<Post>
)