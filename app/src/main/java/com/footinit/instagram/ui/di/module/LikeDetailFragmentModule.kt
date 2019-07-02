package com.footinit.instagram.ui.di.module

import androidx.lifecycle.ViewModelProviders
import com.footinit.instagram.data.repository.post.PostRepository
import com.footinit.instagram.ui.main.likedetail.LikeDetailFragment
import com.footinit.instagram.ui.main.likedetail.LikeDetailViewModel
import com.footinit.instagram.ui.main.likedetail.LikeDetailViewModelImpl
import com.footinit.instagram.ui.main.likedetail.likes.LikeAdapter
import com.footinit.instagram.utils.common.ViewModelProviderFactory
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class LikeDetailFragmentModule(private val fragment: LikeDetailFragment) : BaseFragmentModule(fragment) {

    @Provides
    fun provideLikeDetailViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        postRepository: PostRepository
    ): LikeDetailViewModel =
        ViewModelProviders.of(
            fragment,
            ViewModelProviderFactory(LikeDetailViewModelImpl::class) {
                LikeDetailViewModelImpl(schedulerProvider, compositeDisposable, networkHelper, postRepository)
            }
        ).get(LikeDetailViewModelImpl::class.java)

    @Provides
    fun provideLikeAdapter() = LikeAdapter(mutableListOf())
}