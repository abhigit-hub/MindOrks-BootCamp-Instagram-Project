package com.footinit.instagram.extension

import android.util.Patterns

fun String.isValidEmail(): Boolean = !isBlank() &&
        Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidName(): Boolean = !isBlank() && this.length > 0