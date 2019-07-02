package com.footinit.instagram.ui.signup

import android.os.Bundle
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.footinit.instagram.ui.base.BaseViewModel
import com.footinit.instagram.utils.common.Event
import com.footinit.instagram.utils.common.Resource

interface SignUpViewModel : BaseViewModel {

    fun getLoginNavigation(): LiveData<Event<Bundle>>

    fun getFullNameField(): MutableLiveData<String>

    fun getEmailField(): MutableLiveData<String>

    fun getPasswordField(): MutableLiveData<String>

    fun getSigningIn(): LiveData<Boolean>

    fun getFullNameValidation(): LiveData<Resource<Int>>

    fun getEmailValidation(): LiveData<Resource<Int>>

    fun getPasswordValidation(): LiveData<Resource<Int>>

    fun onSignUp()

    fun clearFullName()

    fun clearEmail()

    fun clearPassword()

    fun navigateToLogin()

    fun validateInputs(s: Editable)
}