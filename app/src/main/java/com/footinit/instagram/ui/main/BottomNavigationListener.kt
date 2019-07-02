package com.footinit.instagram.ui.main

enum class BottomMenuNavigation { HOME, ADD_PHOTOS, PROFILE }

interface BottomMenuNavigationListener {
    fun onMenuSelected(itemId: Int): Boolean
}