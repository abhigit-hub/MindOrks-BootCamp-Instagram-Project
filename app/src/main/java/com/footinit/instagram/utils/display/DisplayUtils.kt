package com.footinit.instagram.utils.display

import android.content.res.Resources

object DisplayUtils {

    fun dpToPx(dp: Float): Float = dp * Resources.getSystem().displayMetrics.density

    fun pxToDp(px: Float): Float = px / (Resources.getSystem().displayMetrics.densityDpi.toFloat() / 160f)

    fun getScreenWidth() = Resources.getSystem().displayMetrics.widthPixels

    fun getScreenHeight() = Resources.getSystem().displayMetrics.heightPixels
}