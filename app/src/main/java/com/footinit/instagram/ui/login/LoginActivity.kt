package com.footinit.instagram.ui.login

import android.os.Bundle
import androidx.lifecycle.Observer
import com.footinit.instagram.R
import com.footinit.instagram.databinding.ActivityLoginBinding
import com.footinit.instagram.ui.base.BaseActivity
import com.footinit.instagram.ui.main.MainActivity
import com.footinit.instagram.ui.signup.SignUpActivity

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    companion object {
        const val TAG = "LoginActivity"
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun setupView(savedInstanceState: Bundle?) {
        binding.viewModel = viewModel

        viewModel.getDummyNavigation().observe(this, Observer {
            it.getIfNotHandled()?.run {
                startActivity(MainActivity::class, this)
                finish()
            }
        })

        viewModel.getSignUpNavigation().observe(this, Observer {
            it.getIfNotHandled()?.run { startActivity(SignUpActivity::class, this) }
        })
    }
}
