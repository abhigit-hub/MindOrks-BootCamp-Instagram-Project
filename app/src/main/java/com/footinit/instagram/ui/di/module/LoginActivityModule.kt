package com.footinit.instagram.ui.di.module

import androidx.lifecycle.ViewModelProviders
import com.footinit.instagram.data.repository.user.UserRepository
import com.footinit.instagram.ui.login.LoginActivity
import com.footinit.instagram.ui.login.LoginViewModel
import com.footinit.instagram.ui.login.LoginViewModelImpl
import com.footinit.instagram.utils.common.ViewModelProviderFactory
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class LoginActivityModule(private val activity: LoginActivity) : BaseActivityModule(activity) {

    @Provides
    fun provideLoginViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        userRepository: UserRepository
    ): LoginViewModel =
        ViewModelProviders.of(activity,
            ViewModelProviderFactory(LoginViewModelImpl::class) {
                LoginViewModelImpl(schedulerProvider, compositeDisposable, networkHelper, userRepository)
            })
            .get(LoginViewModelImpl::class.java)
}