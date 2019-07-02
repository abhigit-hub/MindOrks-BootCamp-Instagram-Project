package com.footinit.instagram.data.local.db.entity

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "user_entity",
    indices = [Index(value = arrayOf("postId"))],
    foreignKeys = [
        ForeignKey(
            parentColumns = ["postId"],
            childColumns = ["postId"],
            entity = Post::class,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "primaryId")
    var primaryId: Long,

    @Expose
    @ColumnInfo(name = "userId")
    @SerializedName("id")
    var id: String,

    @ColumnInfo(name = "postId")
    var postId: String,

    @Expose
    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String,

    @Expose
    @ColumnInfo(name = "profilePicUrl")
    @SerializedName("profilePicUrl")
    var profilePicUrl: String?
)