package com.footinit.instagram.ui.di.module

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.footinit.instagram.ui.base.BaseActivity
import com.footinit.instagram.ui.di.ActivityContext
import com.footinit.instagram.utils.rx.RxSchedulerProvider
import com.footinit.instagram.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
abstract class BaseActivityModule(private val activity: BaseActivity<*, *>) {

    @Provides
    @ActivityContext
    fun provideContext(): Context = activity

    @Provides
    fun provideActivity(): BaseActivity<*, *> = activity

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    fun provideLinearLayoutManager(activity: BaseActivity<*, *>): LinearLayoutManager = LinearLayoutManager(activity)

}