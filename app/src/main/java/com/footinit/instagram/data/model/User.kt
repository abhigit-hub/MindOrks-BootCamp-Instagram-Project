package com.footinit.instagram.data.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @Expose
    @ColumnInfo(name = "userId")
    @SerializedName("id")
    var id: String,

    @Expose
    @SerializedName("name")
    var name: String,

    @Expose
    @SerializedName("profilePicUrl")
    var profilePicUrl: String?
)