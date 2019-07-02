package com.footinit.instagram.utils.common

import androidx.room.TypeConverter
import java.util.*

class TimeStampConverter {

    @TypeConverter
    fun toDate(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun toLong(value: Date?): Long? {
        return value?.time
    }
}