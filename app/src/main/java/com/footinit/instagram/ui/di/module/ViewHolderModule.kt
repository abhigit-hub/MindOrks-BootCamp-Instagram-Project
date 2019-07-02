package com.footinit.instagram.ui.di.module

import com.footinit.instagram.ui.base.BaseViewHolder
import com.footinit.instagram.utils.rx.RxSchedulerProvider
import com.footinit.instagram.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class ViewHolderModule(private val viewHolder: BaseViewHolder<*>) {

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()
}