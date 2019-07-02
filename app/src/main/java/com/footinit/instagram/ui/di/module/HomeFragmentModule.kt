package com.footinit.instagram.ui.di.module

import androidx.lifecycle.ViewModelProviders
import com.footinit.instagram.data.repository.post.PostRepository
import com.footinit.instagram.ui.main.home.HomeFragment
import com.footinit.instagram.ui.main.home.HomeViewModel
import com.footinit.instagram.ui.main.home.HomeViewModelImpl
import com.footinit.instagram.ui.main.home.posts.PostAdapter
import com.footinit.instagram.utils.common.ViewModelProviderFactory
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class HomeFragmentModule(private val fragment: HomeFragment) : BaseFragmentModule(fragment) {

    @Provides
    fun provideHomeFragmentViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        postRepository: PostRepository
    ): HomeViewModel =
        ViewModelProviders.of(
            fragment,
            ViewModelProviderFactory(HomeViewModelImpl::class) {
                HomeViewModelImpl(schedulerProvider, compositeDisposable, networkHelper, postRepository)
            }
        ).get(HomeViewModelImpl::class.java)

    @Provides
    fun providePostAdapter() = PostAdapter(mutableListOf())
}