package com.footinit.instagram.ui.base

import com.footinit.instagram.R
import com.footinit.instagram.utils.network.NetworkHelper
import javax.net.ssl.HttpsURLConnection

abstract class BaseItemViewModel<T : Any>(private val networkHelper: NetworkHelper) {

    var data: T? = null
    lateinit var messageIdListener: (Int) -> Unit
    lateinit var messageListener: (String) -> Unit

    fun updateData(data: T) {
        this.data = data
        setUp()
    }

    abstract fun setUp()

    fun checkInternetConnectionWithMessage(): Boolean =
        if (networkHelper.isNetworkConnected()) {
            true
        } else {
            messageIdListener(R.string.network_connection_error)
            false
        }

    protected fun handleNetworkError(err: Throwable?) =
        err?.let {
            networkHelper.castToNetworkError(it).run {
                when (status) {
                    -1 ->
                        messageIdListener(R.string.network_default_error)
                    0 ->
                        messageIdListener(R.string.server_connection_error)
                    HttpsURLConnection.HTTP_INTERNAL_ERROR ->
                        messageIdListener(R.string.network_internal_error)
                    HttpsURLConnection.HTTP_UNAVAILABLE ->
                        messageIdListener(R.string.network_server_not_available)
                    else ->
                        messageListener(message)
                }
            }
        }
}