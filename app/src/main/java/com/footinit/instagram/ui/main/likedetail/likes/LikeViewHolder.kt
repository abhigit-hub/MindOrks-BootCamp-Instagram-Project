package com.footinit.instagram.ui.main.likedetail.likes

import androidx.databinding.ViewDataBinding
import com.footinit.instagram.data.local.db.entity.UserEntity
import com.footinit.instagram.databinding.ItemLikeBinding
import com.footinit.instagram.ui.base.BaseItemViewModel
import com.footinit.instagram.ui.base.BaseViewHolder
import javax.inject.Inject

class LikeViewHolder(override val binding: ItemLikeBinding) : BaseViewHolder<ViewDataBinding>(binding) {

    @Inject
    lateinit var viewModel: LikeViewModel

    override fun getViewModel(): BaseItemViewModel<*> = viewModel

    override fun bindViewModel(data: Any) {
        viewModel.updateData(data as UserEntity)
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }
}