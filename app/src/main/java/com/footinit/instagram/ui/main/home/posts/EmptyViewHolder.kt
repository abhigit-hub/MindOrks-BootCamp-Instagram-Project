package com.footinit.instagram.ui.main.home.posts

import androidx.databinding.ViewDataBinding
import com.footinit.instagram.databinding.ItemViewEmptyBinding
import com.footinit.instagram.ui.base.BaseItemViewModel
import com.footinit.instagram.ui.base.BaseViewHolder
import javax.inject.Inject

class EmptyViewHolder(override val binding: ItemViewEmptyBinding) : BaseViewHolder<ViewDataBinding>(binding) {

    @Inject
    lateinit var viewModel: EmptyViewModel

    override fun getViewModel(): BaseItemViewModel<*> = viewModel

    override fun bindViewModel(data: Any) {
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}