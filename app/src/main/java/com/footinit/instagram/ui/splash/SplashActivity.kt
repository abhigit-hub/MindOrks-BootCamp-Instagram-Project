package com.footinit.instagram.ui.splash

import android.os.Bundle
import androidx.lifecycle.Observer
import com.footinit.instagram.R
import com.footinit.instagram.databinding.ActivitySplashBinding
import com.footinit.instagram.ui.base.BaseActivity
import com.footinit.instagram.ui.login.LoginActivity
import com.footinit.instagram.ui.main.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    companion object {
        const val TAG = "SplashActivity"
    }

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun setupView(savedInstanceState: Bundle?) {
        binding.viewModel = viewModel

        viewModel.getMainNavigation().observe(this, Observer {
            it.getIfNotHandled()?.run { startActivity(MainActivity::class, this) }
        })

        viewModel.getLoginNavigation().observe(this, Observer {
            it.getIfNotHandled()?.run { startActivity(LoginActivity::class, this) }
        })
    }
}
