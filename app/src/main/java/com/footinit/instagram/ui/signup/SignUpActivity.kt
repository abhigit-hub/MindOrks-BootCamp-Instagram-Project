package com.footinit.instagram.ui.signup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.footinit.instagram.R
import com.footinit.instagram.commons.Status
import com.footinit.instagram.extension.setEditTextBackground
import com.footinit.instagram.extension.showSnack
import kotlinx.android.synthetic.main.activity_signup.*

class SignUpActivity : AppCompatActivity() {

    lateinit var viewmodel: SignUpViewModel


    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        viewmodel = ViewModelProviders.of(this@SignUpActivity).get(SignUpViewModel::class.java)

        observeAllEvents()

        setUpListeners()

        setUpView()
    }

    private fun observeAllEvents() {
        viewmodel.fullNameValidationEvent.observe(this@SignUpActivity,
            Observer {
                if (it.status == Status.ERROR) updateValidationState(
                    R.string.signup_enterfullname,
                    edittext_signup_fullname
                )
                else updateValidationState(R.string.all_empty_string, edittext_signup_fullname)
            })

        viewmodel.emailValidationEvent.observe(this@SignUpActivity,
            Observer {
                if (it.status == Status.ERROR) updateValidationState(R.string.all_invalid_email, edittext_signup_email)
                else updateValidationState(R.string.all_empty_string, edittext_signup_email)
            })

        viewmodel.passwordValidationEvent.observe(this@SignUpActivity,
            Observer {
                if (it.status == Status.ERROR) updateValidationState(
                    R.string.all_invalid_password,
                    edittext_signup_password
                )
                else updateValidationState(R.string.all_empty_string, edittext_signup_password)
            })

        viewmodel.navigationEvent.observe(this@SignUpActivity,
            Observer { if (it) showValidationSuccess() })
    }

    private fun setUpView() {
        edittext_signup_fullname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateValidationState(R.string.all_empty_string, edittext_signup_fullname)
            }
        })

        edittext_signup_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateValidationState(R.string.all_empty_string, edittext_signup_email)
            }

        })

        edittext_signup_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateValidationState(R.string.all_empty_string, edittext_signup_password)
            }

        })
    }

    private fun setUpListeners() {
        textview_signuptologin.setOnClickListener { finishSignUpActivity() }
        imageView_signup_clearfullname.setOnClickListener { clearFullName() }
        imageView_signup_clearemail.setOnClickListener { clearEmail() }
        imageView_signup_clearpassword.setOnClickListener { clearPassword() }

        button_signup.setOnClickListener {
            viewmodel.onSignUpClicked(
                edittext_signup_fullname.text.toString(),
                edittext_signup_email.text.toString(),
                edittext_signup_password.text.toString()
            )
        }
    }

    private fun finishSignUpActivity() {
        finish()
    }

    private fun clearFullName() {
        edittext_signup_fullname.text.clear()
    }

    private fun clearEmail() {
        edittext_signup_email.text.clear()
    }

    private fun clearPassword() {
        edittext_signup_password.text.clear()
    }

    private fun updateValidationState(resource: Int, editText: EditText) {
        val message = getString(resource)
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