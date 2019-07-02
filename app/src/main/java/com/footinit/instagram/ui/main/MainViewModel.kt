package com.footinit.instagram.ui.main

import androidx.lifecycle.LiveData
import com.footinit.instagram.ui.base.BaseViewModel
import com.footinit.instagram.utils.common.Event

interface MainViewModel : BaseViewModel {
    fun getMainNavigation(): LiveData<Event<BottomMenuNavigation>>

    fun getBottomNavigationListener(): BottomMenuNavigationListener
}