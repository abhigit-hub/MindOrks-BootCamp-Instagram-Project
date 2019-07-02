package com.footinit.instagram.ui.signup

import android.os.Bundle
import androidx.lifecycle.Observer
import com.footinit.instagram.R
import com.footinit.instagram.databinding.ActivitySignupBinding
import com.footinit.instagram.ui.base.BaseActivity

class SignUpActivity : BaseActivity<ActivitySignupBinding, SignUpViewModel>() {

    companion object {
        const val TAG = "SignUpActivity"
    }

    override fun getLayoutId(): Int = R.layout.activity_signup

    override fun setupView(savedInstanceState: Bundle?) {
        binding.viewModel = viewModel

        viewModel.getLoginNavigation().observe(this, Observer {
            it.getIfNotHandled()?.run {
                finish()
            }
        })
    }
}