package com.footinit.instagram.ui.main.addphotos.gallery

import androidx.databinding.ViewDataBinding
import com.footinit.instagram.databinding.ItemGalleryBinding
import com.footinit.instagram.ui.base.BaseItemViewModel
import com.footinit.instagram.ui.base.BaseViewHolder
import javax.inject.Inject

class GalleryViewHolder(override val binding: ItemGalleryBinding) : BaseViewHolder<ViewDataBinding>(binding) {

    @Inject
    lateinit var viewModel: GalleryViewModel

    override fun getViewModel(): BaseItemViewModel<*> = viewModel

    init {
        viewModel.onImageSelectedEvent = {
            it?.let {
                callback?.let { viewHolderCallback ->
                    (viewHolderCallback as GalleryViewHolderCallback).onImageSelected(it)
                }
            }
        }
    }

    override fun bindViewModel(data: Any) {
        binding.viewModel = viewModel
        viewModel.updateData(data as String)
        binding.executePendingBindings()
    }

    interface GalleryViewHolderCallback : ViewHolderCallback {
        fun onImageSelected(path: String)
    }
}