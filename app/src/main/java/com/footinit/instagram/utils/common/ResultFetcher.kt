package com.footinit.instagram.utils.common

interface ResultFetcher<T : Any> {
    fun fetch(): T?
}