package com.footinit.instagram.ui.main.home.posts

import androidx.databinding.ViewDataBinding
import com.footinit.instagram.data.local.db.entity.PostWithUser
import com.footinit.instagram.databinding.ItemPostBinding
import com.footinit.instagram.ui.base.BaseItemViewModel
import com.footinit.instagram.ui.base.BaseViewHolder
import javax.inject.Inject

class PostViewHolder(override val binding: ItemPostBinding) : BaseViewHolder<ViewDataBinding>(binding) {

    @Inject
    lateinit var viewModel: PostViewModel

    override fun getViewModel(): BaseItemViewModel<*> = viewModel

    init {
        viewModel.onLikeCountClickEvent = { postId ->
            callback?.let { viewHolderCallback ->
                (viewHolderCallback as PostViewHolderCallback).onLikeCountClicked(postId)
            }
        }
    }

    override fun bindViewModel(data: Any) {
        viewModel.updateData(data as PostWithUser)
        binding.viewModel = viewModel
        binding.executePendingBindings()
    }

    interface PostViewHolderCallback : ViewHolderCallback {
        fun onLikeCountClicked(postId: String)
    }
}