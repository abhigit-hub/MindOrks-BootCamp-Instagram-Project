package com.footinit.instagram.ui.di.component

import com.footinit.instagram.data.repository.di.RepositoryModule
import com.footinit.instagram.di.component.ApplicationComponent
import com.footinit.instagram.ui.di.FragmentScope
import com.footinit.instagram.ui.di.module.ProfileFragmentModule
import com.footinit.instagram.ui.main.profile.ProfileFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [
        ProfileFragmentModule::class,
        RepositoryModule::class
    ]
)
interface ProfileFragmentComponent {
    fun inject(fragment: ProfileFragment)
}