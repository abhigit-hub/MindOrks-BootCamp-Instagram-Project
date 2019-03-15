package com.footinit.instagram.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.footinit.instagram.R
import com.footinit.instagram.commons.Status
import com.footinit.instagram.extension.setEditTextBackground
import com.footinit.instagram.extension.showSnack
import com.footinit.instagram.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var viewmodel: LoginViewModel

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        viewmodel = ViewModelProviders.of(this@LoginActivity).get(LoginViewModel::class.java)

        observeAllEvents()
        setUpListeners()
    }

    private fun setUpListeners() {
        textview_logintosignup.setOnClickListener { openSignUpActivity() }

        imageView_login_clearemail.setOnClickListener { clearEmail() }

        imageView_login_clearpassword.setOnClickListener { clearPassword() }

        button_login.setOnClickListener {
            viewmodel.onLoginClicked(edittext_login_email.text.toString(), edittext_login_password.text.toString())
        }
    }

    private fun observeAllEvents() {
        viewmodel.emailValidationEvent.observe(this@LoginActivity,
            Observer {
                if (it.status == Status.ERROR) updateValidationState(R.string.all_invalid_email, edittext_login_email)
                else updateValidationState(R.string.all_empty_string, edittext_login_email)
            })

        viewmodel.passwordValidationEvent.observe(this@LoginActivity,
            Observer {
                if (it.status == Status.ERROR) updateValidationState(R.string.all_invalid_password, edittext_login_password)
                else updateValidationState(R.string.all_empty_string, edittext_login_password)
            })

        viewmodel.navigationEvent.observe(this@LoginActivity,
            Observer { if (it) showValidationSuccess() })
    }

    private fun openSignUpActivity() {
        startActivity(SignUpActivity.getStartIntent(this@LoginActivity))
    }

    private fun clearEmail() {
        edittext_login_email.text.clear()
    }

    private fun clearPassword() {
        edittext_login_password.text.clear()
    }

    private fun updateValidationState(resourceId: Int, editText: EditText) {
        val message: String = getString(resourceId)
        if (message.trim().isBlank()) {
            editText.setEditTextBackground(R.drawable.style_edittext_normal)
        } else {
            editText.setEditTextBackground(R.drawable.style_edittext_error)
            root.showSnack(message)
        }
    }

    private fun showValidationSuccess() {
        root.showSnack(getString(R.string.all_validation_success))
    }
}
