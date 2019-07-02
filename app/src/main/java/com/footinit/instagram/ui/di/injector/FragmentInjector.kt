package com.footinit.instagram.ui.di.injector

import com.footinit.instagram.di.injector.ApplicationInjector
import com.footinit.instagram.ui.base.BaseFragment
import com.footinit.instagram.ui.di.component.*
import com.footinit.instagram.ui.di.module.*
import com.footinit.instagram.ui.main.addphotos.AddPhotosFragment
import com.footinit.instagram.ui.main.home.HomeFragment
import com.footinit.instagram.ui.main.likedetail.LikeDetailFragment
import com.footinit.instagram.ui.main.profile.ProfileFragment
import com.footinit.instagram.ui.main.profile.editprofile.EditProfileFragment

object FragmentInjector {

    fun <F : BaseFragment<*, *>> inject(fragment: F) {
        when (fragment) {
            is HomeFragment -> DaggerHomeFragmentComponent
                .builder()
                .applicationComponent(ApplicationInjector.applicationComponent)
                .homeFragmentModule(HomeFragmentModule(fragment))
                .build()
                .inject(fragment)

            is AddPhotosFragment -> DaggerAddPhotosFragmentComponent
                .builder()
                .applicationComponent(ApplicationInjector.applicationComponent)
                .addPhotosFragmentModule(AddPhotosFragmentModule(fragment))
                .build()
                .inject(fragment)
            is ProfileFragment -> DaggerProfileFragmentComponent
                .builder()
                .applicationComponent(ApplicationInjector.applicationComponent)
                .profileFragmentModule(ProfileFragmentModule(fragment))
                .build()
                .inject(fragment)
            is EditProfileFragment -> DaggerEditProfileFragmentComponent
                .builder()
                .applicationComponent(ApplicationInjector.applicationComponent)
                .editProfileFragmentModule(EditProfileFragmentModule(fragment))
                .build()
                .inject(fragment)
            is LikeDetailFragment -> DaggerLikeDetailFragmentComponent
                .builder()
                .applicationComponent(ApplicationInjector.applicationComponent)
                .likeDetailFragmentModule(LikeDetailFragmentModule(fragment))
                .build()
                .inject(fragment)
        }
    }
}