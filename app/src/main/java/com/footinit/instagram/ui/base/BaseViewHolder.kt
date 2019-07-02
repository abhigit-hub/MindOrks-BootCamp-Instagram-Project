package com.footinit.instagram.ui.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.footinit.instagram.ui.di.injector.ViewHolderInjector
import com.footinit.instagram.utils.display.Toaster

abstract class BaseViewHolder<B : ViewDataBinding>(protected open val binding: B) :
    RecyclerView.ViewHolder(binding.root) {

    /*
    * Flag to keep check if the the listener are registered for the corresponding viewholder.
    * If the listener are registered already, we do not need to register again, as the Viewholder are anyways bound
    * to the same instance of the viewmodel
    * */
    private var isRegistered = false
    internal var callback: ViewHolderCallback? = null

    init {
        ViewHolderInjector.inject(this)
    }

    protected abstract fun getViewModel(): BaseItemViewModel<*>

    protected abstract fun bindViewModel(data: Any)

    open fun onBind(data: Any) {
        bindViewModel(data)
        if (!isRegistered) {
            isRegistered = true
            registerListeners()
        }
    }

    /*
    * Register in onBind(), because we need to be sure that the viewmodel instance is injected via dependency injection
    * */
    private fun registerListeners() {
        getViewModel().messageIdListener = {
            Toaster.show(
                binding.root.context,
                binding.root.context.getString(it)
            )
        }

        getViewModel().messageListener = { Toaster.show(binding.root.context, it) }
    }

    fun setCallback(callback: ViewHolderCallback) {
        this.callback = callback
    }

    fun removeCallback() {
        this.callback = null
    }

    interface ViewHolderCallback
}