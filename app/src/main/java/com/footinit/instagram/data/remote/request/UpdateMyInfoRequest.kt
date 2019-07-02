package com.footinit.instagram.data.remote.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UpdateMyInfoRequest(

    @Expose
    @SerializedName("name")
    var name: String,

    @Expose
    @SerializedName("profilePicUrl")
    var profilePicUrl: String,

    @Expose
    @SerializedName("tagline")
    var tagline: String
)