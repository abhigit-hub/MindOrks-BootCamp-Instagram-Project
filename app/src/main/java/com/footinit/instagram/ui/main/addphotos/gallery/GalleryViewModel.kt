package com.footinit.instagram.ui.main.addphotos.gallery

import com.footinit.instagram.ui.base.BaseItemViewModel
import com.footinit.instagram.ui.di.ViewModelScope
import com.footinit.instagram.utils.network.NetworkHelper
import javax.inject.Inject

@ViewModelScope
class GalleryViewModel @Inject constructor(networkHelper: NetworkHelper) : BaseItemViewModel<String>(networkHelper) {

    lateinit var onImageSelectedEvent: (String?) -> Unit
    private var imagePath: String? = null

    fun getImagePath() = imagePath

    override fun setUp() {
        imagePath = data
    }

    fun onImageSelected() {
        onImageSelectedEvent(imagePath)
    }
}