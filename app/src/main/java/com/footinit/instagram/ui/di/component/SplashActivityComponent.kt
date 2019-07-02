package com.footinit.instagram.ui.di.component

import com.footinit.instagram.data.repository.di.RepositoryModule
import com.footinit.instagram.di.component.ApplicationComponent
import com.footinit.instagram.ui.di.ActivityScope
import com.footinit.instagram.ui.di.module.SplashActivityModule
import com.footinit.instagram.ui.splash.SplashActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [
        SplashActivityModule::class,
        RepositoryModule::class
    ]
)
interface SplashActivityComponent {

    fun inject(activity: SplashActivity)
}