package com.footinit.instagram.ui.main.likedetail.likes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.footinit.instagram.data.local.db.entity.UserEntity
import com.footinit.instagram.databinding.ItemLikeBinding
import com.footinit.instagram.ui.base.BaseViewHolder

class LikeAdapter(private val list: MutableList<UserEntity>) : RecyclerView.Adapter<BaseViewHolder<ViewDataBinding>>() {

    companion object {
        val TAG = "LikeAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewDataBinding> =
        LikeViewHolder(
            ItemLikeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) =
        holder.onBind(list[position])

    fun appendData(newList: List<UserEntity>) {
        if (newList.isNotEmpty()) {
            list.clear()
            list.addAll(newList)
            notifyDataSetChanged()
        }
    }
}