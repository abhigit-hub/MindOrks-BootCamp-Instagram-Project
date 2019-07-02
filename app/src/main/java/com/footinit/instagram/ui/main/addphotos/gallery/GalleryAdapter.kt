package com.footinit.instagram.ui.main.addphotos.gallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.footinit.instagram.databinding.ItemCameraBinding
import com.footinit.instagram.databinding.ItemGalleryBinding
import com.footinit.instagram.ui.base.BaseViewHolder

class GalleryAdapter(private val list: MutableList<String>) : RecyclerView.Adapter<BaseViewHolder<ViewDataBinding>>(),
    GalleryViewHolder.GalleryViewHolderCallback, CameraViewHolder.CameraViewHolderCallback {

    companion object {
        const val TAG = "GalleryAdapter"
        const val SPAN_COUNT = 3
        const val VIEW_CAMERA = 1
        const val VIEW_GALLERY = 2
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
            VIEW_CAMERA -> CameraViewHolder(
                ItemCameraBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            VIEW_GALLERY -> GalleryViewHolder(
                ItemGalleryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> CameraViewHolder(
                ItemCameraBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int =
        when (position) {
            0 -> VIEW_CAMERA
            else -> VIEW_GALLERY
        }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewDataBinding>, position: Int) {
        holder.onBind(list[position])
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder<ViewDataBinding>) {
        super.onViewAttachedToWindow(holder)
        holder.setCallback(this)
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<ViewDataBinding>) {
        super.onViewDetachedFromWindow(holder)
        holder.removeCallback()
    }

    fun appendData(newList: MutableList<String>) {
        //Add item for Camera
        if (this.list.size == 0)
            this.list.add("")

        this.list.addAll(newList)
        notifyDataSetChanged()
    }

    interface FragmentCallback {
        fun onCameraModeSelected()

        fun onImageSelected(path: String)
    }

    override fun onImageSelected(path: String) {
        callback?.onImageSelected(path)
    }

    override fun onCameraModeSelected() {
        callback?.onCameraModeSelected()
    }
}