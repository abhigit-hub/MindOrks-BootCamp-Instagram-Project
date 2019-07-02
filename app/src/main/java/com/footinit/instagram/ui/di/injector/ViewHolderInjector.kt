package com.footinit.instagram.ui.di.injector

import com.footinit.instagram.di.injector.ApplicationInjector
import com.footinit.instagram.ui.base.BaseViewHolder
import com.footinit.instagram.ui.di.component.DaggerViewHolderComponent
import com.footinit.instagram.ui.di.module.ViewHolderModule
import com.footinit.instagram.ui.main.addphotos.gallery.CameraViewHolder
import com.footinit.instagram.ui.main.addphotos.gallery.GalleryViewHolder
import com.footinit.instagram.ui.main.home.posts.EmptyViewHolder
import com.footinit.instagram.ui.main.home.posts.PostViewHolder
import com.footinit.instagram.ui.main.likedetail.likes.LikeViewHolder

object ViewHolderInjector {

    fun <VH : BaseViewHolder<*>> inject(viewHolder: VH) {
        when (viewHolder) {
            is PostViewHolder -> DaggerViewHolderComponent
                .builder()
                .applicationComponent(ApplicationInjector.applicationComponent)
                .viewHolderModule(ViewHolderModule(viewHolder))
                .build()
                .inject(viewHolder)
            is EmptyViewHolder -> DaggerViewHolderComponent
                .builder()
                .applicationComponent(ApplicationInjector.applicationComponent)
                .viewHolderModule(ViewHolderModule(viewHolder))
                .build()
                .inject(viewHolder)
            is LikeViewHolder -> DaggerViewHolderComponent
                .builder()
                .applicationComponent(ApplicationInjector.applicationComponent)
                .viewHolderModule(ViewHolderModule(viewHolder))
                .build()
                .inject(viewHolder)
            is CameraViewHolder -> DaggerViewHolderComponent
                .builder()
                .applicationComponent(ApplicationInjector.applicationComponent)
                .viewHolderModule(ViewHolderModule((viewHolder)))
                .build()
                .inject(viewHolder)
            is GalleryViewHolder -> DaggerViewHolderComponent
                .builder()
                .applicationComponent(ApplicationInjector.applicationComponent)
                .viewHolderModule(ViewHolderModule(viewHolder))
                .build()
                .inject(viewHolder)
        }
    }
}