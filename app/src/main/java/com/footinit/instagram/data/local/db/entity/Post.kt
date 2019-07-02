package com.footinit.instagram.data.local.db.entity

import androidx.room.*
import com.footinit.instagram.data.model.User
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(
    tableName = "posts",
    indices = [Index(value = ["postId"], unique = true)]
)
data class Post(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "primaryId")
    var primaryId: Long,

    @Expose
    @ColumnInfo(name = "postId")
    @SerializedName("id")
    var id: String,

    @Expose
    @ColumnInfo(name = "imgUrl")
    @SerializedName("imgUrl")
    var imgUrl: String,

    @Expose
    @ColumnInfo(name = "imgWidth")
    @SerializedName("imgWidth")
    var imgWidth: Int?,

    @Expose
    @ColumnInfo(name = "imgHeight")
    @SerializedName("imgHeight")
    var imgHeight: Int?,

    @Embedded
    @Expose
    @SerializedName("user")
    var user: User?,

    @Ignore
    var likedBy: List<UserEntity>?,

    @Expose
    @ColumnInfo(name = "createdAt")
    @SerializedName("createdAt")
    var createdAt: Date?,

    @ColumnInfo(name = "isMyPost")
    var isMyPost: Boolean
) {
    constructor() : this(0, "", "", 0, 0, null, null, null, false)
}