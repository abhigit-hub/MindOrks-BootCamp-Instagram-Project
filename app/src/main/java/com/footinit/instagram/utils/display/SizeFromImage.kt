package com.footinit.instagram.utils.display

import android.graphics.BitmapFactory


class SizeFromImage(private val path: String) : ISize {
    private var width: Int = 0
    private var height: Int = 0

    init {
        width = -1
        height = -1
    }

    override fun width(): Int {
        if (width == -1) {
            init()
        }
        return width
    }

    override fun height(): Int {
        if (height == -1) {
            init()
        }
        return height
    }

    private fun init() {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        width = options.outWidth
        height = options.outHeight
    }

    override fun equals(o: Any?): Boolean {
        return SizeEqualsAndHashCode.equals(this, o)

    }

    override fun hashCode(): Int {
        return SizeEqualsAndHashCode.hashCode(this)
    }
}