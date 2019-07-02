package com.footinit.instagram.ui.di.component

import com.footinit.instagram.data.repository.di.RepositoryModule
import com.footinit.instagram.di.component.ApplicationComponent
import com.footinit.instagram.ui.di.FragmentScope
import com.footinit.instagram.ui.di.module.AddPhotosFragmentModule
import com.footinit.instagram.ui.main.addphotos.AddPhotosFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [
        AddPhotosFragmentModule::class,
        RepositoryModule::class
    ]
)
interface AddPhotosFragmentComponent {
    fun inject(fragment: AddPhotosFragment)
}