package com.footinit.instagram.extension

import android.widget.EditText

fun EditText.setEditTextBackground(resource: Int) =
    this.setBackgroundDrawable(context.getDrawable(resource))