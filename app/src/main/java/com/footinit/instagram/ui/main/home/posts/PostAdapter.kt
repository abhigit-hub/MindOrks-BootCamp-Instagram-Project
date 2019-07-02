package com.footinit.instagram.ui.main.home.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.footinit.instagram.data.local.db.entity.PostWithUser
import com.footinit.instagram.databinding.ItemPostBinding
import com.footinit.instagram.databinding.ItemViewEmptyBinding
import com.footinit.instagram.ui.base.BaseViewHolder

class PostAdapter(private val list: MutableList<PostWithUser>) :
    RecyclerView.Adapter<BaseViewHolder<ViewDataBinding>>(), PostViewHolder.PostViewHolderCallback {

    companion object {
        val TAG = "PostAdapter"
        const val VIEW_TYPE_POST = 0
        const val VIEW_TYPE_EMPTY = 1
    }

    private var callback: FragmentCallback? = null

    fun setCallback(callback: FragmentCallback) {
        this.callback = callback
    }

    fun removeCallback() {
        this.callback = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewDataBinding> =
        when (viewType) {
            VIEW_TYPE_POST -> PostViewHolder(
                ItemPostBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            VIEW_TYPE_EMPTY -> EmptyViewHolder(
                ItemViewEmptyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> EmptyViewHolder(
                ItemViewEmptyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun getItemCount(): Int =
        when (list.size) {
            0 -> 1
            else -> list.size
        }

    override fun getItemViewType(position: Int): Int =
        when (list.size) {
            0 -> VIEW_TYPE_EMPTY
            else -> VIEW_TYPE_POST
        }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) =
        when (list.size) {
            0 -> holder.onBind(Any())
            else -> holder.onBind(list[position])
        }

    override fun onViewAttachedToWindow(holder: BaseViewHolder<ViewDataBinding>) {
        super.onViewAttachedToWindow(holder)
        holder.setCallback(this@PostAdapter)
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<ViewDataBinding>) {
        holder.removeCallback()
        super.onViewDetachedFromWindow(holder)
    }

    fun appendData(newItems: List<PostWithUser>) {
        if (newItems.isNotEmpty()) {
            list.clear()
            list.addAll(newItems)
            notifyDataSetChanged()
        }
    }

    interface FragmentCallback {
        fun showUserLikes(postId: String)
    }

    override fun onLikeCountClicked(postId: String) {
        callback?.showUserLikes(postId)
    }
}