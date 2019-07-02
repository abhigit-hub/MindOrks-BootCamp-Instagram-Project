package com.footinit.instagram.ui.di.component

import com.footinit.instagram.data.repository.di.RepositoryModule
import com.footinit.instagram.di.component.ApplicationComponent
import com.footinit.instagram.ui.di.ActivityScope
import com.footinit.instagram.ui.di.module.LoginActivityModule
import com.footinit.instagram.ui.login.LoginActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [
        LoginActivityModule::class,
        RepositoryModule::class
    ]
)
interface LoginActivityComponent {
    fun inject(activity: LoginActivity)
}