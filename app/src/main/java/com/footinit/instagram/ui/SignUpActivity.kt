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
import com.footinit.instagram.viewmodel.SignUpViewModel

class SignUpActivity : AppCompatActivity() {

    lateinit var viewmodel: SignUpViewModel

    @BindView(R.id.edittext_signup_fullname)
    lateinit var etFullName: EditText

    @BindView(R.id.edittext_signup_email)
    lateinit var etEmail: EditText

    @BindView(R.id.edittext_signup_password)
    lateinit var etPassword: EditText

    @BindView(R.id.root)
    lateinit var clRoot: ConstraintLayout

    @OnClick(R.id.textview_signuptologin)
    fun onLoginClicked() {
        viewmodel.onLoginClicked()
    }

    @OnClick(R.id.imageView_signup_clearfullname)
    fun onClearFullName() {
        viewmodel.onClearFullName()
    }

    @OnClick(R.id.imageView_signup_clearemail)
    fun onClearEmail() {
        viewmodel.onClearEmail()
    }

    @OnClick(R.id.imageView_signup_clearpassword)
    fun onClearPassword() {
        viewmodel.onClearPassword()
    }

    @OnClick(R.id.button_signup)
    fun onSignUpClicked() {
        viewmodel.onSignUpClicked(
            etFullName.text.toString(),
            etEmail.text.toString(),
            etPassword.text.toString()
        )
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        ButterKnife.bind(this@SignUpActivity)

        viewmodel = ViewModelProviders.of(this@SignUpActivity).get(SignUpViewModel::class.java)

        observeAllEvents()
    }

    private fun observeAllEvents() {
        observeFinishSignUpActivity()
        observeClearFullNameEvent()
        observeClearEmailEvent()
        observeClearPasswordEvent()
        observeFullNameValidationEvent()
        observeEmailValidationEvent()
        observePasswordValidationEvent()
        observeSignUpValidationEvent()
    }

    private fun observeFinishSignUpActivity() {
        viewmodel.finishSignUpActivityEvent.observe(this@SignUpActivity,
            Observer { status -> if (status) finishSignUpActivity() })
    }

    private fun observeClearFullNameEvent() {
        viewmodel.clearFullNameEvent.observe(this@SignUpActivity,
            Observer { status -> if (status) clearFullName() })
    }

    private fun observeClearEmailEvent() {
        viewmodel.clearEmailEvent.observe(this@SignUpActivity,
            Observer { status -> if (status) clearEmail() })
    }

    private fun observeClearPasswordEvent() {
        viewmodel.clearPasswordEvent.observe(this@SignUpActivity,
            Observer { status -> if (status) clearPassword() })
    }

    private fun observeFullNameValidationEvent() {
        viewmodel.fullNameValidationEvent.observe(this@SignUpActivity,
            Observer { status -> updateFullNameValidationState(status) })
    }

    private fun observeEmailValidationEvent() {
        viewmodel.emailValidationEvent.observe(this@SignUpActivity,
            Observer { status -> updateEmailValidationState(status) })
    }

    private fun observePasswordValidationEvent() {
        viewmodel.passwordValidationEvent.observe(this@SignUpActivity,
            Observer { status -> updatePasswordValidationState(status) })
    }

    private fun observeSignUpValidationEvent() {
        viewmodel.signUpValidationEvent.observe(this@SignUpActivity,
            Observer { status -> if (status) showValidationSuccess() })
    }


    /*
    * ACTIONS BASED ON EVENTS FROM VIEW MODEL
    * */
    private fun finishSignUpActivity() {
        finish()
    }

    private fun clearFullName() {
        etFullName.text.clear()
    }

    private fun clearEmail() {
        etEmail.text.clear()
    }

    private fun clearPassword() {
        etPassword.text.clear()
    }

    private fun updateFullNameValidationState(resource: Int) {
        val message = getString(resource)
        if (message.trim().isBlank()) {
            //fullname validation success
            etFullName.setEditTextBackground(R.drawable.style_edittext_normal)
        } else {
            //fullname validation error
            etFullName.setEditTextBackground(R.drawable.style_edittext_error)
            clRoot.showSnack(message)
        }
    }

    private fun updateEmailValidationState(resource: Int) {
        val message = getString(resource)
        if (message.trim().isBlank()) {
            //email validation success
            etEmail.setEditTextBackground(R.drawable.style_edittext_normal)
        } else {
            //email validation error
            etEmail.setEditTextBackground(R.drawable.style_edittext_error)
            clRoot.showSnack(message)
        }
    }

    private fun updatePasswordValidationState(resource: Int) {
        val message = getString(resource)
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