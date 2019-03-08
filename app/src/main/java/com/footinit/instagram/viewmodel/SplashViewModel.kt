package com.footinit.instagram.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class SplashViewModel : ViewModel() {

    /*
    * NAVIGATION
    * These events will be observed from the UI thread
    * */
    val openSplashActivityEvent: MutableLiveData<Boolean> = MutableLiveData()


    init {
        startActivityWithDelay()
    }


    /*
    * NAVIGATION
    * Commands to update Events, which are observed from UI thread
    * */
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