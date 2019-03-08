package com.footinit.instagram.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.footinit.instagram.R
import com.footinit.instagram.extension.isValidEmail
import com.footinit.instagram.extension.isValidName
import com.footinit.instagram.extension.isValidPassword

class SignUpViewModel : ViewModel() {

    /*
    * NAVIGATION
    * These events will be observed from the UI thread
    * */
    val finishSignUpActivityEvent: MutableLiveData<Boolean> = MutableLiveData()

    val clearFullNameEvent: MutableLiveData<Boolean> = MutableLiveData()

    val clearEmailEvent: MutableLiveData<Boolean> = MutableLiveData()

    val clearPasswordEvent: MutableLiveData<Boolean> = MutableLiveData()

    val fullNameValidationEvent: MutableLiveData<Int> = MutableLiveData()

    val emailValidationEvent: MutableLiveData<Int> = MutableLiveData()

    val passwordValidationEvent: MutableLiveData<Int> = MutableLiveData()

    val signUpValidationEvent: MutableLiveData<Boolean> = MutableLiveData()

    /*
    * NAVIGATION
    * Commands to update Events, which are observed from UI thread
    * */
    fun onLoginClicked() {
        finishSignUpActivityEvent.postValue(true)
    }

    fun onClearFullName() {
        clearFullNameEvent.postValue(true)
    }

    fun onClearEmail() {
        clearEmailEvent.postValue(true)
    }

    fun onClearPassword() {
        clearPasswordEvent.postValue(true)
    }

    fun updateFullNameValidationStatus(message: Int) {
        fullNameValidationEvent.postValue(message)
    }

    fun updateEmailValidationStatus(message: Int) {
        emailValidationEvent.postValue(message)
    }

    fun updatePasswordValidationStatus(message: Int) {
        passwordValidationEvent.postValue(message)
    }

    fun onSignUpClicked(fullName: String, email: String, password: String) {
        var validationStatus = true

        if (!fullName.isValidName()) {
            validationStatus = false
            updateFullNameValidationStatus(R.string.all_invalid_name)
        } else {
            updateFullNameValidationStatus(R.string.all_empty_string)
        }

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
            signUpValidationEvent.postValue(true)
    }
}