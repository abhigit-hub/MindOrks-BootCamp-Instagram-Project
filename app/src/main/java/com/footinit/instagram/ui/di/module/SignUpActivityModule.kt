package com.footinit.instagram.ui.di.module

import androidx.lifecycle.ViewModelProviders
import com.footinit.instagram.data.repository.user.UserRepository
import com.footinit.instagram.ui.signup.SignUpActivity
import com.footinit.instagram.ui.signup.SignUpViewModel
import com.footinit.instagram.ui.signup.SignUpViewModelImpl
import com.footinit.instagram.utils.common.ViewModelProviderFactory
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class SignUpActivityModule(private val activity: SignUpActivity) : BaseActivityModule(activity) {

    @Provides
    fun provideSignUpViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        userRepository: UserRepository
    ): SignUpViewModel =
        ViewModelProviders.of(activity,
            ViewModelProviderFactory(SignUpViewModelImpl::class) {
                SignUpViewModelImpl(schedulerProvider, compositeDisposable, networkHelper, userRepository)
            })
            .get(SignUpViewModelImpl::class.java)
}