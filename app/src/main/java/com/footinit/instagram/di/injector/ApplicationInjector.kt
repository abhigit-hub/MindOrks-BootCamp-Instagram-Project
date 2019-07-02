package com.footinit.instagram.di.injector

import com.footinit.instagram.InstagramApplication
import com.footinit.instagram.data.local.db.di.DatabaseModule
import com.footinit.instagram.data.local.prefs.di.PreferenceModule
import com.footinit.instagram.data.remote.di.NetworkModule
import com.footinit.instagram.data.repository.di.RepositoryModule
import com.footinit.instagram.di.component.ApplicationComponent
import com.footinit.instagram.di.component.DaggerApplicationComponent
import com.footinit.instagram.di.module.ApplicationModule

object ApplicationInjector {

    lateinit var applicationComponent: ApplicationComponent

    fun inject(application: InstagramApplication) {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(application))
            .databaseModule(DatabaseModule())
            .networkModule(NetworkModule())
            .preferenceModule(PreferenceModule())
            .repositoryModule(RepositoryModule())
            .build()
        applicationComponent.inject(application)
    }
}