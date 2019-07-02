package com.footinit.instagram.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.footinit.instagram.R
import com.footinit.instagram.ui.base.BaseViewModelImpl
import com.footinit.instagram.utils.common.Event
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainViewModelImpl(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
) : BaseViewModelImpl(schedulerProvider, compositeDisposable, networkHelper), MainViewModel {


    private val mainNavigation: MutableLiveData<Event<BottomMenuNavigation>> = MutableLiveData()
    private val bottomNavigationListener = object : BottomMenuNavigationListener {
        override fun onMenuSelected(itemId: Int): Boolean {
            when (itemId) {
                R.id.menu_home -> mainNavigation.postValue(Event(BottomMenuNavigation.HOME))
                R.id.menu_add_photos -> mainNavigation.postValue(Event(BottomMenuNavigation.ADD_PHOTOS))
                R.id.menu_profile -> mainNavigation.postValue(Event(BottomMenuNavigation.PROFILE))
                else -> return false
            }
            return true
        }
    }

    override fun getMainNavigation(): LiveData<Event<BottomMenuNavigation>> = mainNavigation
    override fun getBottomNavigationListener(): BottomMenuNavigationListener = bottomNavigationListener

    override fun onViewCreated() {
        mainNavigation.postValue(Event(BottomMenuNavigation.HOME))
    }
}