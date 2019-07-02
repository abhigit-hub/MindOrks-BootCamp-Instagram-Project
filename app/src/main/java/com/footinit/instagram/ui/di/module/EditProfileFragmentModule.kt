package com.footinit.instagram.ui.di.module

import androidx.lifecycle.ViewModelProviders
import com.footinit.instagram.data.remote.RequestHeaders
import com.footinit.instagram.data.repository.post.PostRepository
import com.footinit.instagram.data.repository.profileinfo.ProfileInfoRepository
import com.footinit.instagram.data.repository.user.UserRepository
import com.footinit.instagram.ui.main.profile.SharedProfileViewModel
import com.footinit.instagram.ui.main.profile.SharedProfileViewModelImpl
import com.footinit.instagram.ui.main.profile.editprofile.EditProfileFragment
import com.footinit.instagram.utils.common.ViewModelProviderFactory
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class EditProfileFragmentModule(private val fragment: EditProfileFragment) : BaseFragmentModule(fragment) {

    @Provides
    fun provideSharedProfileViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        userRepository: UserRepository,
        profileInfoRepository: ProfileInfoRepository,
        postRepository: PostRepository,
        requestHeaders: RequestHeaders
    ): SharedProfileViewModel =
        ViewModelProviders.of(
            fragment,
            ViewModelProviderFactory(SharedProfileViewModelImpl::class) {
                SharedProfileViewModelImpl(
                    schedulerProvider, compositeDisposable,
                    networkHelper, userRepository, profileInfoRepository,
                    postRepository, requestHeaders
                )
            }
        ).get(SharedProfileViewModelImpl::class.java)
}