package com.footinit.instagram.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import java.util.*

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    val openSplashActivityEvent: MutableLiveData<Boolean> = MutableLiveData()


    init {
        startActivityWithDelay()
    }

    private fun onOpenSplashActivityEvent() {
        openSplashActivityEvent.postValue(true)
    }

    private fun startActivityWithDelay() {
        Timer().schedule(object : TimerTask() {
            override fun run() {
                onOpenSplashActivityEvent()
            }
        }, 2000)
    }
}