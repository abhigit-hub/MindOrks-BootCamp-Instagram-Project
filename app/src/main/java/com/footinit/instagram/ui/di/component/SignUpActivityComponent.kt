package com.footinit.instagram.ui.di.component

import com.footinit.instagram.data.repository.di.RepositoryModule
import com.footinit.instagram.di.component.ApplicationComponent
import com.footinit.instagram.ui.di.ActivityScope
import com.footinit.instagram.ui.di.module.SignUpActivityModule
import com.footinit.instagram.ui.signup.SignUpActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [
        SignUpActivityModule::class,
        RepositoryModule::class
    ]
)
interface SignUpActivityComponent {
    fun inject(activity: SignUpActivity)
}