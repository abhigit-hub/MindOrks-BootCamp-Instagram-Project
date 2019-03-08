package com.footinit.instagram.extension

import android.util.Patterns

fun String.isValidEmail(): Boolean = this.isNotEmpty() &&
        Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword(): Boolean = this.trim().isNotEmpty() && this.length > 6

fun String.isValidName(): Boolean = this.trim().isNotEmpty() && this.length > 0