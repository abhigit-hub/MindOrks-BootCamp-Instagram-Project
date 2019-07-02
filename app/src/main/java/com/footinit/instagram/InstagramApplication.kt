package com.footinit.instagram

import android.app.Application
import com.facebook.stetho.Stetho
import com.footinit.instagram.di.injector.ApplicationInjector

class InstagramApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ApplicationInjector.inject(this)
        Stetho.initializeWithDefaults(this)
    }
}