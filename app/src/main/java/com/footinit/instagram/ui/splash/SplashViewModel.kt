package com.footinit.instagram.ui.splash

import android.os.Bundle
import androidx.lifecycle.LiveData
import com.footinit.instagram.ui.base.BaseViewModel
import com.footinit.instagram.utils.common.Event

interface SplashViewModel : BaseViewModel {

    fun getMainNavigation(): LiveData<Event<Bundle>>

    fun getLoginNavigation(): LiveData<Event<Bundle>>
}