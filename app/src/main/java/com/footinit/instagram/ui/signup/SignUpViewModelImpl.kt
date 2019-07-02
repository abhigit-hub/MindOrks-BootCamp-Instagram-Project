package com.footinit.instagram.ui.signup

import android.os.Bundle
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.footinit.instagram.R
import com.footinit.instagram.data.repository.user.UserRepository
import com.footinit.instagram.ui.base.BaseViewModelImpl
import com.footinit.instagram.utils.common.*
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class SignUpViewModelImpl(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    val userRepository: UserRepository
) : BaseViewModelImpl(schedulerProvider, compositeDisposable, networkHelper), SignUpViewModel {

    private val validationList: MutableLiveData<List<Validation>> = MutableLiveData()
    private val loginNavigation: MutableLiveData<Event<Bundle>> = MutableLiveData()
    private val fullNameField: MutableLiveData<String> = MutableLiveData()
    private val emailField: MutableLiveData<String> = MutableLiveData()
    private val passwordField: MutableLiveData<String> = MutableLiveData()
    private val signingIn: MutableLiveData<Boolean> = MutableLiveData()


    override fun getLoginNavigation(): LiveData<Event<Bundle>> = loginNavigation
    override fun getFullNameField(): MutableLiveData<String> = fullNameField
    override fun getEmailField(): MutableLiveData<String> = emailField
    override fun getPasswordField(): MutableLiveData<String> = passwordField
    override fun getSigningIn(): LiveData<Boolean> = signingIn
    override fun getFullNameValidation(): LiveData<Resource<Int>> = transformValidation(Validation.Field.FULLNAME)
    override fun getEmailValidation(): LiveData<Resource<Int>> = transformValidation(Validation.Field.EMAIL)
    override fun getPasswordValidation(): LiveData<Resource<Int>> = transformValidation(Validation.Field.PASSWORD)
    override fun navigateToLogin() = loginNavigation.postValue(Event(Bundle()))

    override fun onSignUp() {
        val fullName = fullNameField.value
        val email = emailField.value
        val password = passwordField.value

        val validations = Validator.validateSignUpFields(fullName, email, password)
        validationList.postValue(validations)

        if (validations.isNotEmpty() && fullName != null && email != null && password != null) {
            val successValidation = validations.filter { it.resource.status == Status.SUCCESS }

            if (successValidation.size == validations.size && checkInternetConnectionWithMessage()) {
                signingIn.postValue(true)

                compositeDisposable.addAll(
                    userRepository.doUserSignUp(fullName, email, password)
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                            {
                                messageStringIdLiveData.postValue(Resource.success(R.string.signup_success))
                                signingIn.postValue(false)
                                navigateToLogin()
                            },
                            {
                                handleNetworkError(it)
                                signingIn.postValue(false)
                            }
                        )
                )
            }
        }
    }

    override fun clearFullName() = fullNameField.postValue("")

    override fun clearEmail() = emailField.postValue("")

    override fun clearPassword() = passwordField.postValue("")

    override fun validateInputs(s: Editable) {
        val fullName = fullNameField.value
        val email = emailField.value
        val password = passwordField.value

        val validations = Validator.validateSignUpFields(fullName, email, password)
        validationList.postValue(validations)
    }

    override fun onViewCreated() {}

    private fun transformValidation(field: Validation.Field) =
        Transformations.map(validationList) {
            it.find { validation -> validation.field == field }
                ?.run { return@run resource }
                ?: Resource.unknown()
        }
}