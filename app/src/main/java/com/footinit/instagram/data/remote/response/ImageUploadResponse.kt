package com.footinit.instagram.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImageUploadResponse(

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
    var data: Data
) {

    data class Data(
        @Expose
        @SerializedName("imageUrl")
        var imageUrl: String
    )
}