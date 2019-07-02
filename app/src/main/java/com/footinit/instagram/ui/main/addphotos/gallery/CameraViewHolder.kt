package com.footinit.instagram.ui.main.addphotos.gallery

import androidx.databinding.ViewDataBinding
import com.footinit.instagram.databinding.ItemCameraBinding
import com.footinit.instagram.ui.base.BaseItemViewModel
import com.footinit.instagram.ui.base.BaseViewHolder
import javax.inject.Inject

class CameraViewHolder(override val binding: ItemCameraBinding) : BaseViewHolder<ViewDataBinding>(binding) {

    @Inject
    lateinit var viewModel: CameraViewModel

    override fun getViewModel(): BaseItemViewModel<*> = viewModel

    init {
        viewModel.onCameraModeSelectedEvent = {
            callback?.let { viewHolderCallback -> (viewHolderCallback as CameraViewHolderCallback).onCameraModeSelected() }
        }
    }

    override fun bindViewModel(data: Any) {
        binding.viewModel = viewModel
        viewModel.updateData(data as String)
        binding.executePendingBindings()
    }

    interface CameraViewHolderCallback : ViewHolderCallback {
        fun onCameraModeSelected()
    }
}