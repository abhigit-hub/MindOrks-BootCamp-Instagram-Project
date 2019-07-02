package com.footinit.instagram.ui.main.addphotos

import android.os.Bundle
import androidx.lifecycle.LiveData
import com.footinit.instagram.ui.base.BaseViewModel
import com.footinit.instagram.utils.common.Event
import com.footinit.instagram.utils.common.LoadMoreListener
import okhttp3.MultipartBody

interface AddPhotosViewModel : BaseViewModel {

    fun getImagePathList(): LiveData<MutableList<String>>

    fun readImagesFromGallery()

    fun getBackNavigation(): LiveData<Event<Bundle>>

    fun getIsLoading(): LiveData<Boolean>

    fun getLoadMoreListener(): LoadMoreListener

    fun onBackNavigation()

    fun onCreatePost(image: MultipartBody.Part, imageResolution: Pair<Int, Int>)
}