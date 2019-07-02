package com.footinit.instagram.ui.base

import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.footinit.instagram.ui.di.injector.ActivityInjector
import com.footinit.instagram.utils.display.Toaster
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseActivity<B : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {

    companion object {
        const val DEFAULT_BUNDLE_KEY = "DEFAULT_BUNDLE_KEY"
    }

    @Inject
    lateinit var viewModel: VM

    lateinit var binding: B

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    protected abstract fun setupView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityInjector.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
        setupObservers()
        setupView(savedInstanceState)
        viewModel.onViewCreated()
    }

    protected open fun setupObservers() {
        viewModel.getMessage().observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.getMessageStringId().observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.getSnackBar().observe(this, Observer {
            it.data?.run { showSnackBarMessage(this) }
        })

        viewModel.getSnackBarStringId().observe(this, Observer {
            it.data?.run { showSnackBarMessage(this) }
        })
    }

    fun showMessage(message: String) = Toaster.show(applicationContext, message)

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    fun showSnackBarMessage(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

    fun showSnackBarMessage(@StringRes resId: Int) = showSnackBarMessage(getString(resId))

    fun closeKeyboard() {
        val view = currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
        }
    }

    fun openKeyboard() {
        val view = currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

    open fun goBack() = onBackPressed()

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0)
            supportFragmentManager.popBackStackImmediate()
        else super.onBackPressed()
    }

    open fun addFragment(@IdRes viewId: Int, fragment: Fragment, tag: String, backstack: Boolean = true) =
        supportFragmentManager
            .beginTransaction()
            .add(viewId, fragment, tag)
            .apply { if (backstack) addToBackStack(null) }
            .commitAllowingStateLoss()

    open fun addFragment(fragment: Fragment, tag: String, backstack: Boolean = true) =
        supportFragmentManager
            .beginTransaction()
            .add(android.R.id.content, fragment, tag)
            .apply { if (backstack) addToBackStack(null) }
            .commitAllowingStateLoss()

    fun <T : BaseActivity<*, *>> startActivity(kclass: KClass<T>, bundle: Bundle) {
        val intent = Intent(this, kclass.java)
        intent.putExtra(DEFAULT_BUNDLE_KEY, bundle)
        startActivity(intent)
    }
}