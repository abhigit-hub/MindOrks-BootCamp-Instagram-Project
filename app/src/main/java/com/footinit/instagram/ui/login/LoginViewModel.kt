package com.footinit.instagram.ui.login

import android.os.Bundle
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.footinit.instagram.ui.base.BaseViewModel
import com.footinit.instagram.utils.common.Event
import com.footinit.instagram.utils.common.Resource

interface LoginViewModel : BaseViewModel {

    fun getDummyNavigation(): LiveData<Event<Bundle>>

    fun getSignUpNavigation(): LiveData<Event<Bundle>>

    fun getEmailField(): MutableLiveData<String>

    fun getPasswordField(): MutableLiveData<String>

    fun getLoggingIn(): LiveData<Boolean>

    fun getEmailValidation(): LiveData<Resource<Int>>

    fun getPasswordValidation(): LiveData<Resource<Int>>

    fun onLogin()

    fun clearEmail()

    fun clearPassword()

    fun navigateToSignUp()

    fun validateInputs(s: Editable)
}