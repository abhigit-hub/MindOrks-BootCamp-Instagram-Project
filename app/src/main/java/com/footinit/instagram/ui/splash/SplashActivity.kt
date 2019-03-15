package com.footinit.instagram.ui.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.footinit.instagram.R
import com.footinit.instagram.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    lateinit var viewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        viewModel = ViewModelProviders.of(this@SplashActivity).get(SplashViewModel::class.java)

        observeOpenSplashActivity()
    }

    private fun observeOpenSplashActivity() {
        viewModel.openSplashActivityEvent.observe(this@SplashActivity,
            Observer { status -> if (status) openSplashActivity() })
    }

    private fun openSplashActivity() {
        startActivity(LoginActivity.getStartIntent(this@SplashActivity))
        finish()
    }
}
