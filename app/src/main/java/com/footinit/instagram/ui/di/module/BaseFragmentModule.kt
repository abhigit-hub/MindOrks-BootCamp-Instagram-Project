package com.footinit.instagram.ui.di.module

import androidx.recyclerview.widget.LinearLayoutManager
import com.footinit.instagram.ui.base.BaseFragment
import com.footinit.instagram.utils.rx.RxSchedulerProvider
import com.footinit.instagram.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
abstract class BaseFragmentModule(private val fragment: BaseFragment<*, *>) {

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager =
        LinearLayoutManager(fragment.context)

}