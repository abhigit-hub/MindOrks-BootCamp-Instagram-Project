package com.footinit.instagram.ui.di.module

import androidx.lifecycle.ViewModelProviders
import com.footinit.instagram.data.repository.user.UserRepository
import com.footinit.instagram.ui.splash.SplashActivity
import com.footinit.instagram.ui.splash.SplashViewModel
import com.footinit.instagram.ui.splash.SplashViewModelImpl
import com.footinit.instagram.utils.common.ViewModelProviderFactory
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class SplashActivityModule(private val activity: SplashActivity) : BaseActivityModule(activity) {

    @Provides
    fun provideSplashViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        userRepository: UserRepository
    ): SplashViewModel =
        ViewModelProviders.of(activity,
            ViewModelProviderFactory(SplashViewModelImpl::class) {
                SplashViewModelImpl(schedulerProvider, compositeDisposable, networkHelper, userRepository)
            })
            .get(SplashViewModelImpl::class.java)


}