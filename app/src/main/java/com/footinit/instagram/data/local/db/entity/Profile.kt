package com.footinit.instagram.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Nullable

@Entity(tableName = "profile")
data class Profile(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "primaryId")
    @SerializedName("primaryId")
    var primaryId: Long = 0,

    @Expose
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id: String? = "",

    @Expose
    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String? = "",

    @Expose
    @ColumnInfo(name = "profilePicUrl")
    @SerializedName("profilePicUrl")
    var profilePicUrl: String? = "",

    @Expose
    @ColumnInfo(name = "tagline")
    @SerializedName("tagline")
    @Nullable
    var tagline: String? = "",

    @Expose
    @ColumnInfo(name = "email")
    @SerializedName("email")
    @Nullable
    var email: String? = ""
)