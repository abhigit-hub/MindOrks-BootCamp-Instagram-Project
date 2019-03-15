package com.footinit.instagram.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.footinit.instagram.commons.Resource
import com.footinit.instagram.commons.Status
import com.footinit.instagram.utils.Validation
import com.footinit.instagram.utils.Validator

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val loginValidationEvent: MutableLiveData<List<Validation>> by lazy {
        MutableLiveData<List<Validation>>()
    }

    val emailValidationEvent: LiveData<Resource<Any>> by lazy {
        transformLoginValidationEvent(Validation.Field.EMAIL)
    }

    val passwordValidationEvent: LiveData<Resource<Any>> by lazy {
        transformLoginValidationEvent(Validation.Field.PASSWORD)
    }

    val navigationEvent: LiveData<Boolean> by lazy {
        Transformations.map(loginValidationEvent) {
            it.isNotEmpty() && it.filter {
                it.resource.status == Status.SUCCESS
            }.size == it.size
        }
    }

    private fun transformLoginValidationEvent(field: Validation.Field) =
        Transformations.map(loginValidationEvent) {
            it.find { validation -> validation.field == field }
                ?.run { return@run this.resource }
                ?: Resource(Status.UNKNOWN)
        }

    fun onLoginClicked(email: String, password: String) {
        loginValidationEvent.value = Validator.validateLoginFields(email, password)
    }
}