package com.footinit.instagram.ui.main.addphotos.gallery

import com.footinit.instagram.ui.base.BaseItemViewModel
import com.footinit.instagram.ui.di.ViewModelScope
import com.footinit.instagram.utils.network.NetworkHelper
import javax.inject.Inject

@ViewModelScope
class CameraViewModel @Inject constructor(networkHelper: NetworkHelper) : BaseItemViewModel<String>(networkHelper) {

    lateinit var onCameraModeSelectedEvent: (Unit) -> Unit

    fun onCameraViewClicked() {
        onCameraModeSelectedEvent(Unit)
    }

    override fun setUp() {}
}