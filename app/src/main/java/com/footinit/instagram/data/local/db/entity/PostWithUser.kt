package com.footinit.instagram.data.local.db.entity

import androidx.room.Embedded
import androidx.room.Relation

class PostWithUser(

    @Embedded
    var post: Post?,

    @Relation(parentColumn = "postId", entityColumn = "postId")
    var likedBy: MutableList<UserEntity>?
) {
    constructor() : this(null, null)
}