package com.footinit.instagram.ui.di.module

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.footinit.instagram.data.repository.post.PostRepository
import com.footinit.instagram.ui.main.addphotos.AddPhotosFragment
import com.footinit.instagram.ui.main.addphotos.AddPhotosViewModel
import com.footinit.instagram.ui.main.addphotos.AddPhotosViewModelImpl
import com.footinit.instagram.ui.main.addphotos.gallery.GalleryAdapter
import com.footinit.instagram.utils.common.ViewModelProviderFactory
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class AddPhotosFragmentModule(private val fragment: AddPhotosFragment) : BaseFragmentModule(fragment) {

    @Provides
    fun provideAddPhotosViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        postRepository: PostRepository
    ): AddPhotosViewModel =
        ViewModelProviders.of(
            fragment,
            ViewModelProviderFactory(AddPhotosViewModelImpl::class) {
                AddPhotosViewModelImpl(schedulerProvider, compositeDisposable, networkHelper, postRepository)
            }
        ).get(AddPhotosViewModelImpl::class.java)

    @Provides
    fun provideGalleryAdapter() = GalleryAdapter(mutableListOf())

    @Provides
    fun provideGridLayoutManager(): GridLayoutManager = GridLayoutManager(fragment.context, GalleryAdapter.SPAN_COUNT)
}