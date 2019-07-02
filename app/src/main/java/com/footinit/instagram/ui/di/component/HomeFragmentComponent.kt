package com.footinit.instagram.ui.di.component

import com.footinit.instagram.data.repository.di.RepositoryModule
import com.footinit.instagram.di.component.ApplicationComponent
import com.footinit.instagram.ui.di.FragmentScope
import com.footinit.instagram.ui.di.module.HomeFragmentModule
import com.footinit.instagram.ui.main.home.HomeFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [
        HomeFragmentModule::class,
        RepositoryModule::class
    ]
)
interface HomeFragmentComponent {
    fun inject(fragment: HomeFragment)
}