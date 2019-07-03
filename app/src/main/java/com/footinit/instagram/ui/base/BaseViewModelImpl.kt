package com.footinit.instagram.ui.base

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.footinit.instagram.R
import com.footinit.instagram.di.injector.ApplicationInjector
import com.footinit.instagram.utils.common.Resource
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.net.ssl.HttpsURLConnection


abstract class BaseViewModelImpl(
    protected val schedulerProvider: SchedulerProvider,
    protected val compositeDisposable: CompositeDisposable,
    protected val networkHelper: NetworkHelper
) : AndroidViewModel(ApplicationInjector.applicationComponent.getApplication()), BaseViewModel {

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    protected val messageStringIdLiveData: MutableLiveData<Resource<Int>> = MutableLiveData()

    protected val messageLiveData: MutableLiveData<Resource<String>> = MutableLiveData()

    protected val snackBarStringIdLiveData: MutableLiveData<Resource<Int>> = MutableLiveData()

    protected val snackBarLiveData: MutableLiveData<Resource<String>> = MutableLiveData()

    override fun getMessageStringId(): LiveData<Resource<Int>> = messageStringIdLiveData

    override fun getMessage(): LiveData<Resource<String>> = messageLiveData

    override fun getSnackBarStringId(): LiveData<Resource<Int>> = snackBarStringIdLiveData

    override fun getSnackBar(): LiveData<Resource<String>> = snackBarLiveData

    fun checkInternetConnectionWithMessage(): Boolean =
        if (networkHelper.isNetworkConnected()) {
            true
        } else {
            messageStringIdLiveData.postValue(Resource.error(R.string.network_connection_error))
            false
        }

    fun checkInternetConnection(): Boolean = networkHelper.isNetworkConnected()

    protected fun handleNetworkError(err: Throwable?) =
        err?.let {
            networkHelper.castToNetworkError(it).run {
                when (status) {
                    -1 -> messageStringIdLiveData.postValue(Resource.error(R.string.network_default_error))
                    0 -> messageStringIdLiveData.postValue(Resource.error(R.string.server_connection_error))
                    HttpsURLConnection.HTTP_UNAUTHORIZED -> {
                        forcedLogoutUser()
                        messageStringIdLiveData.postValue(Resource.error(R.string.server_unauthorized_user))
                    }
                    HttpsURLConnection.HTTP_INTERNAL_ERROR ->
                        messageStringIdLiveData.postValue(Resource.error(R.string.network_internal_error))
                    HttpsURLConnection.HTTP_UNAVAILABLE ->
                        messageStringIdLiveData.postValue(Resource.error(R.string.network_server_not_available))
                    else -> messageLiveData.postValue(Resource.error(message))
                }
            }
        }

    protected open fun forcedLogoutUser() {
        // do something
    }
}