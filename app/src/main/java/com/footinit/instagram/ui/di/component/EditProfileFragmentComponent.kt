package com.footinit.instagram.ui.di.component

import com.footinit.instagram.data.repository.di.RepositoryModule
import com.footinit.instagram.di.component.ApplicationComponent
import com.footinit.instagram.ui.di.FragmentScope
import com.footinit.instagram.ui.di.module.EditProfileFragmentModule
import com.footinit.instagram.ui.main.profile.editprofile.EditProfileFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [
        EditProfileFragmentModule::class,
        RepositoryModule::class
    ]
)
interface EditProfileFragmentComponent {
    fun inject(fragment: EditProfileFragment)
}