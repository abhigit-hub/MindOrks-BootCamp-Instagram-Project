package com.footinit.instagram.ui.login

import android.os.Bundle
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.footinit.instagram.data.repository.user.UserRepository
import com.footinit.instagram.ui.base.BaseViewModelImpl
import com.footinit.instagram.utils.common.*
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class LoginViewModelImpl(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    val userRepository: UserRepository
) : BaseViewModelImpl(schedulerProvider, compositeDisposable, networkHelper), LoginViewModel {

    companion object {
        val TAG = "LoginViewModelImpl"
    }

    private val validationList: MutableLiveData<List<Validation>> = MutableLiveData()
    private val dummyNavigation: MutableLiveData<Event<Bundle>> = MutableLiveData()
    private val signUpNavigation: MutableLiveData<Event<Bundle>> = MutableLiveData()
    private val emailField: MutableLiveData<String> = MutableLiveData()
    private val passwordField: MutableLiveData<String> = MutableLiveData()
    private val loggingIn: MutableLiveData<Boolean> = MutableLiveData()

    override fun getDummyNavigation(): LiveData<Event<Bundle>> = dummyNavigation
    override fun getSignUpNavigation(): LiveData<Event<Bundle>> = signUpNavigation
    override fun getEmailField(): MutableLiveData<String> = emailField
    override fun getPasswordField(): MutableLiveData<String> = passwordField
    override fun getLoggingIn(): LiveData<Boolean> = loggingIn
    override fun getEmailValidation(): LiveData<Resource<Int>> = transformValidation(Validation.Field.EMAIL)
    override fun getPasswordValidation(): LiveData<Resource<Int>> = transformValidation(Validation.Field.PASSWORD)
    override fun clearEmail() = emailField.postValue("")
    override fun clearPassword() = passwordField.postValue("")
    override fun navigateToSignUp() = signUpNavigation.postValue(Event(Bundle()))

    override fun onLogin() {
//        emailField.postValue("test@test.com")
//        passwordField.postValue("test123")

        val email = emailField.value
        val password = passwordField.value

        val validations = Validator.validateLoginFields(email, password)
        validationList.postValue(validations)

        if (validations.isNotEmpty() && email != null && password != null) {
            val successValidation = validations.filter { it.resource.status == Status.SUCCESS }

            if (successValidation.size == validations.size && checkInternetConnectionWithMessage()) {
                loggingIn.postValue(true)
                compositeDisposable.addAll(
                    userRepository.doUserLogin(email, password)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                            {
                                userRepository.saveCurrentUserSession(it)
                                loggingIn.postValue(true)
                                dummyNavigation.postValue(Event(Bundle()))
                            },
                            {
                                handleNetworkError(it)
                                loggingIn.postValue(false)
                            }
                        )
                )
            }
        }
    }

    override fun validateInputs(s: Editable) {
        val email = emailField.value
        val password = passwordField.value

        val validations = Validator.validateLoginFields(email, password)
        validationList.postValue(validations)
    }

    override fun onViewCreated() {}

    private fun transformValidation(field: Validation.Field) =
        Transformations.map(validationList) {
            it.find { validation -> validation.field == field }
                ?.run { return@run this.resource }
                ?: Resource.unknown()
        }
}
