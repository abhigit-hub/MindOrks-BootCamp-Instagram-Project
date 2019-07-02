package com.footinit.instagram.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.footinit.instagram.ui.di.injector.FragmentInjector
import com.footinit.instagram.utils.display.Toaster
import javax.inject.Inject

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    companion object {
        const val DEFAULT_ARGUMENTS_KEY = "DEFAULT_ARGUMENTS_KEY"
    }

    @Inject
    lateinit var viewModel: VM

    lateinit var binding: B

    protected var activity: BaseActivity<out ViewDataBinding, out BaseViewModel>? = null

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun getFragmentTag(): String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<out ViewDataBinding, out BaseViewModel>) this.activity = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        FragmentInjector.inject(this)
        setupObservers()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    protected open fun setupObservers() {
        viewModel.getMessage().observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.getMessageStringId().observe(this, Observer {
            it.data?.run { showMessage(this) }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        viewModel.onViewCreated()
    }

    abstract fun setupView(view: View)

    override fun onDetach() {
        activity = null
        super.onDetach()
    }

    fun showMessage(message: String) = context?.let { Toaster.show(it, message) }

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    fun closeKeyboard() = activity?.closeKeyboard()

    fun openKeyboard() = activity?.openKeyboard()

    fun goBack() = activity?.goBack()

    protected fun setupToolbar(toolbar: Toolbar, @MenuRes menu: Int) {
        toolbar.inflateMenu(menu)
        toolbar.setOnMenuItemClickListener { onOptionsItemSelected(it) }
        onPrepareOptionsMenu(toolbar.menu)
    }
}