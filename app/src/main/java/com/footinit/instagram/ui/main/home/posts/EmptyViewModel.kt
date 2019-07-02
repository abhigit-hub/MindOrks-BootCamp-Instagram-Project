package com.footinit.instagram.ui.main.home.posts

import com.footinit.instagram.data.repository.post.PostRepository
import com.footinit.instagram.ui.base.BaseItemViewModel
import com.footinit.instagram.ui.di.ViewModelScope
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@ViewModelScope
class EmptyViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val compositeDisposable: CompositeDisposable,
    private val networkHelper: NetworkHelper,
    private val postRepository: PostRepository
) : BaseItemViewModel<Any>(networkHelper) {

    override fun setUp() {}
}