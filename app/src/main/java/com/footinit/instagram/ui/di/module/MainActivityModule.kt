package com.footinit.instagram.ui.di.module

import androidx.lifecycle.ViewModelProviders
import com.footinit.instagram.ui.main.MainActivity
import com.footinit.instagram.ui.main.MainViewModel
import com.footinit.instagram.ui.main.MainViewModelImpl
import com.footinit.instagram.utils.common.ViewModelProviderFactory
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class MainActivityModule(private val activity: MainActivity) : BaseActivityModule(activity) {

    @Provides
    fun provideMainViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): MainViewModel =
        ViewModelProviders.of(activity,
            ViewModelProviderFactory(MainViewModelImpl::class) {
                MainViewModelImpl(schedulerProvider, compositeDisposable, networkHelper)
            })
            .get(MainViewModelImpl::class.java)
}