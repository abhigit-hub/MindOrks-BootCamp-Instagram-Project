package com.footinit.instagram.ui.di.component

import com.footinit.instagram.data.repository.di.RepositoryModule
import com.footinit.instagram.di.component.ApplicationComponent
import com.footinit.instagram.ui.di.ViewModelScope
import com.footinit.instagram.ui.di.module.ViewHolderModule
import com.footinit.instagram.ui.main.addphotos.gallery.CameraViewHolder
import com.footinit.instagram.ui.main.addphotos.gallery.GalleryViewHolder
import com.footinit.instagram.ui.main.home.posts.EmptyViewHolder
import com.footinit.instagram.ui.main.home.posts.PostViewHolder
import com.footinit.instagram.ui.main.likedetail.likes.LikeViewHolder
import dagger.Component

@ViewModelScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [
        ViewHolderModule::class,
        RepositoryModule::class
    ]
)
interface ViewHolderComponent {

    fun inject(viewHolder: PostViewHolder)

    fun inject(viewHolder: EmptyViewHolder)

    fun inject(viewHolder: LikeViewHolder)

    fun inject(viewHolder: CameraViewHolder)

    fun inject(viewHolder: GalleryViewHolder)
}