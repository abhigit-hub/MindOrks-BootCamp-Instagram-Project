package com.footinit.instagram.ui.di.component

import com.footinit.instagram.data.repository.di.RepositoryModule
import com.footinit.instagram.di.component.ApplicationComponent
import com.footinit.instagram.ui.di.ActivityScope
import com.footinit.instagram.ui.di.module.MainActivityModule
import com.footinit.instagram.ui.main.MainActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [
        MainActivityModule::class,
        RepositoryModule::class
    ]
)
interface MainActivityComponent {

    fun inject(activity: MainActivity)
}