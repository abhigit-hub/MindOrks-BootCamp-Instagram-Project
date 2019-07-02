package com.footinit.instagram.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "dummy_entity")
data class DummyEntity(

    @PrimaryKey(autoGenerate = true)
    @NotNull
    var id: Long,

    @ColumnInfo(name = "name")
    @NotNull
    var name: String
)