package com.footinit.instagram.ui.base

import androidx.lifecycle.LiveData
import com.footinit.instagram.utils.common.Resource


interface BaseViewModel {

    fun getMessageStringId(): LiveData<Resource<Int>>

    fun getMessage(): LiveData<Resource<String>>

    fun getSnackBarStringId(): LiveData<Resource<Int>>

    fun getSnackBar(): LiveData<Resource<String>>

    fun onViewCreated()
}