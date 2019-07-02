package com.footinit.instagram.data.local.prefs

import android.content.SharedPreferences


fun <T : Any> SharedPreferences.put(key: String, value: T): Unit =
    edit().let {
        when (value) {
            is Int -> it.putInt(key, value)
            is Boolean -> it.putBoolean(key, value)
            is Float -> it.putFloat(key, value)
            is Long -> it.putLong(key, value)
            is String -> it.putString(key, value)
            else -> it.putString(key, it.toString())
        }
        it.apply()
    }

@Suppress("UNCHECKED_CAST")
fun <T : Any> SharedPreferences.get(key: String, default: T): T =
    when (default) {
        is Int -> getInt(key, default) as T
        is Boolean -> getBoolean(key, default) as T
        is Float -> getFloat(key, default) as T
        is Long -> getLong(key, default) as T
        is String -> getString(key, default) as T
        else -> default
    }

fun SharedPreferences.remove(key: String): Unit = edit().remove(key).apply()
