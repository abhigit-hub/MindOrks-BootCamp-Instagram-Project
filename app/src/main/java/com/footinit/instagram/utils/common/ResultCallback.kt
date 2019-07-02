package com.footinit.instagram.utils.common

interface ResultCallback<T : Any> {
    fun onResult(result: T)
}