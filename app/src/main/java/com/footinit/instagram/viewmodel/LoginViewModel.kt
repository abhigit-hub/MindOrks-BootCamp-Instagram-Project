package com.footinit.instagram.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.footinit.instagram.R
import com.footinit.instagram.extension.isValidEmail
import com.footinit.instagram.extension.isValidPassword

class LoginViewModel : ViewModel() {

    /*
    * NAVIGATION
    * These events will be observed from the UI thread
    * */
    val openSignUpActivityEvent: MutableLiveData<Boolean> = MutableLiveData()

    val clearEmailEvent: MutableLiveData<Boolean> = MutableLiveData()

    val clearPasswordEvent: MutableLiveData<Boolean> = MutableLiveData()

    val emailValidationEvent: MutableLiveData<Int> = MutableLiveData()

    val passwordValidationEvent: MutableLiveData<Int> = MutableLiveData()

    val loginValidationEvent: MutableLiveData<Boolean> = MutableLiveData()

    /*
    * NAVIGATION
    * Commands to update Events, which are observed from UI thread
    * */
    fun onSignUpClicked() {
        openSignUpActivityEvent.postValue(true)
    }

    fun onClearEmail() {
        clearEmailEvent.postValue(true)
    }

    fun onClearPassword() {
        clearPasswordEvent.postValue(true)
    }

    fun updateEmailValidationStatus(message: Int) {
        emailValidationEvent.postValue(message)
    }

    fun updatePasswordValidationStatus(message: Int) {
        passwordValidationEvent.postValue(message)
    }

    fun onLoginClicked(email: String, password: String) {
        var validationStatus = true
        if (!email.isValidEmail()) {
            validationStatus = false
            updateEmailValidationStatus(R.string.all_invalid_email)
        } else {
            updateEmailValidationStatus(R.string.all_empty_string)
        }

        if (!password.isValidPassword()) {
            validationStatus = false
            updatePasswordValidationStatus(R.string.all_invalid_password)
        } else {
            updatePasswordValidationStatus(R.string.all_empty_string)
        }

        if (validationStatus)
            loginValidationEvent.postValue(true)
    }
}