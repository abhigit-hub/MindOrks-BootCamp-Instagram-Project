package com.footinit.instagram.utils.common

import android.util.Patterns

fun String.isValidEmail(): Boolean = !isBlank() &&
        Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidName(): Boolean = !isBlank() && this.length > 0

fun String.checkAndCastNullValue() = if (this === Constants.NULL_STRING) null else this
