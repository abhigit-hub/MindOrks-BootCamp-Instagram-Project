package com.footinit.instagram.ui.signup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.footinit.instagram.commons.Resource
import com.footinit.instagram.commons.Status
import com.footinit.instagram.utils.Validation
import com.footinit.instagram.utils.Validator

class SignUpViewModel(application: Application) : AndroidViewModel(application) {

    private val signUpValidationEvent: MutableLiveData<List<Validation>> by lazy {
        MutableLiveData<List<Validation>>()
    }

    val fullNameValidationEvent: LiveData<Resource<Any>> by lazy {
        transformSignUpValidationEvent(Validation.Field.FULLNAME)
    }

    val emailValidationEvent: LiveData<Resource<Any>> by lazy {
        transformSignUpValidationEvent(Validation.Field.EMAIL)
    }

    val passwordValidationEvent: LiveData<Resource<Any>> by lazy {
        transformSignUpValidationEvent(Validation.Field.PASSWORD)
    }

    val navigationEvent: LiveData<Boolean> by lazy {
        Transformations.map(signUpValidationEvent) {
            it.isNotEmpty() && it.filter {
                it.resource.status == Status.SUCCESS
            }.size == it.size
        }
    }

    private fun transformSignUpValidationEvent(field: Validation.Field) =
        Transformations.map(signUpValidationEvent) {
            it.find { it.field == field }
                ?.run { return@run this.resource }
                ?: Resource(Status.UNKNOWN)
        }

    fun onSignUpClicked(fullName: String, email: String, password: String) {
        signUpValidationEvent.value = Validator.validateSignUpFields(fullName, email, password)
    }
}