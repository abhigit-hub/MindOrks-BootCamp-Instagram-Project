package com.footinit.instagram.data.remote.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CreatePostRequest(
    @Expose
    @SerializedName("imgUrl")
    var imgUrl: String,

    @Expose
    @SerializedName("imgWidth")
    var imgWidth: Int,

    @Expose
    @SerializedName("imgHeight")
    var imgHeight: Int
)