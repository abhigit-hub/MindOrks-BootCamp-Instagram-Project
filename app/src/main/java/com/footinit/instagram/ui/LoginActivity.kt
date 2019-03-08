package com.footinit.instagram.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.footinit.instagram.R
import com.footinit.instagram.extension.setEditTextBackground
import com.footinit.instagram.extension.showSnack
import com.footinit.instagram.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    lateinit var viewmodel: LoginViewModel

    @BindView(R.id.edittext_login_email)
    lateinit var etEmail: EditText

    @BindView(R.id.edittext_login_password)
    lateinit var etPassword: EditText

    @BindView(R.id.root)
    lateinit var clRoot: ConstraintLayout

    @OnClick(R.id.textview_logintosignup)
    fun onSignUpClicked() {
        viewmodel.onSignUpClicked()
    }

    @OnClick(R.id.imageView_login_clearemail)
    fun onClearEmail() {
        viewmodel.onClearEmail()
    }

    @OnClick(R.id.imageView_login_clearpassword)
    fun onClearPassword() {
        viewmodel.onClearPassword()
    }

    @OnClick(R.id.button_login)
    fun onLoginClicked() {
        viewmodel.onLoginClicked(etEmail.text.toString(), etPassword.text.toString())
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ButterKnife.bind(this)

        viewmodel = ViewModelProviders.of(this@LoginActivity).get(LoginViewModel::class.java)

        observeAllEvents()
    }

    private fun observeAllEvents() {
        observeOpenSignUpActivity()
        observeClearEmailEvent()
        observeClearPasswordEvent()
        observeEmailValidationEvent()
        observePasswordValidationEvent()
        observeLoginValidationEvent()
    }

    private fun observeOpenSignUpActivity() {
        viewmodel.openSignUpActivityEvent.observe(this@LoginActivity,
            Observer { status -> if (status) openSignUpActivity() })
    }

    private fun observeClearEmailEvent() {
        viewmodel.clearEmailEvent.observe(this@LoginActivity,
            Observer { status -> if (status) clearEmail() })
    }

    private fun observeClearPasswordEvent() {
        viewmodel.clearPasswordEvent.observe(this@LoginActivity,
            Observer { status -> if (status) clearPassword() })
    }

    private fun observeEmailValidationEvent() {
        viewmodel.emailValidationEvent.observe(this@LoginActivity,
            Observer { status -> updateEmailValidationState(status) })
    }

    private fun observePasswordValidationEvent() {
        viewmodel.passwordValidationEvent.observe(this@LoginActivity,
            Observer { status -> updatePasswordValidationState(status) })
    }

    private fun observeLoginValidationEvent() {
        viewmodel.loginValidationEvent.observe(this@LoginActivity,
            Observer { status -> if (status) showValidationSuccess() })
    }


    /*
    * ACTIONS BASED ON EVENTS FROM VIEW MODEL
    * */
    private fun openSignUpActivity() {
        startActivity(SignUpActivity.getStartIntent(this@LoginActivity))
    }

    private fun clearEmail() {
        etEmail.text.clear()
    }

    private fun clearPassword() {
        etPassword.text.clear()
    }

    private fun updateEmailValidationState(resourceId: Int) {
        val message: String = getString(resourceId)
        if (message.trim().isBlank()) {
            //phone validation success
            etEmail.setEditTextBackground(R.drawable.style_edittext_normal)
        } else {
            //phone validation error
            etEmail.setEditTextBackground(R.drawable.style_edittext_error)
            clRoot.showSnack(message)
        }
    }

    private fun updatePasswordValidationState(resourceId: Int) {
        val message: String = getString(resourceId)
        if (message.trim().isBlank()) {
            //password validation success
            etPassword.setEditTextBackground(R.drawable.style_edittext_normal)
        } else {
            //password validation error
            etPassword.setEditTextBackground(R.drawable.style_edittext_error)
            clRoot.showSnack(message)
        }
    }

    private fun showValidationSuccess() {
        clRoot.showSnack(getString(R.string.all_validation_success))
    }
}
