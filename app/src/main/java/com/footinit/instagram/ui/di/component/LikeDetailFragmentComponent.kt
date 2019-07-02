package com.footinit.instagram.ui.di.component

import com.footinit.instagram.data.repository.di.RepositoryModule
import com.footinit.instagram.di.component.ApplicationComponent
import com.footinit.instagram.ui.di.FragmentScope
import com.footinit.instagram.ui.di.module.LikeDetailFragmentModule
import com.footinit.instagram.ui.main.likedetail.LikeDetailFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [
        LikeDetailFragmentModule::class,
        RepositoryModule::class
    ]
)
interface LikeDetailFragmentComponent {
    fun inject(fragment: LikeDetailFragment)
}