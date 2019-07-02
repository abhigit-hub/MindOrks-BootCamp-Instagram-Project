package com.footinit.instagram.ui.splash

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.footinit.instagram.data.repository.user.UserRepository
import com.footinit.instagram.ui.base.BaseViewModelImpl
import com.footinit.instagram.utils.common.Event
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class SplashViewModelImpl(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val userRepository: UserRepository
) : BaseViewModelImpl(schedulerProvider, compositeDisposable, networkHelper), SplashViewModel {

    private val mainNavigation: MutableLiveData<Event<Bundle>> = MutableLiveData()
    private val loginNavigation: MutableLiveData<Event<Bundle>> = MutableLiveData()

    override fun getMainNavigation(): LiveData<Event<Bundle>> = mainNavigation

    override fun getLoginNavigation(): LiveData<Event<Bundle>> = loginNavigation

    override fun onViewCreated() {
        if (userRepository.getCurrentUserSession() != null)
            mainNavigation.postValue(Event(Bundle()))
        else loginNavigation.postValue(Event(Bundle()))
    }
}