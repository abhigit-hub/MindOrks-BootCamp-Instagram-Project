package com.footinit.instagram.ui.main.profile

import android.os.Bundle
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.footinit.instagram.data.local.db.entity.PostWithUser
import com.footinit.instagram.data.local.db.entity.Profile
import com.footinit.instagram.ui.base.BaseViewModel
import com.footinit.instagram.utils.common.Event
import com.footinit.instagram.utils.common.Resource
import okhttp3.MultipartBody

interface SharedProfileViewModel : BaseViewModel {

    /* Commons*/
    fun getIsLoading(): LiveData<Boolean>

    fun getHeaders(): Map<String, String>

    fun getProfileName(): LiveData<String>

    fun getProfilePicUrl(): LiveData<String>

    fun getProfileTagline(): LiveData<String>

    fun getProfileInfo(): LiveData<Profile>


    /* ProfileFragment*/
    fun getPostCount(): LiveData<Int>

    fun getMyPosts(): LiveData<List<PostWithUser>>

    fun navigateToEditProfile()

    fun getEditProfileNavigation(): LiveData<Event<Bundle>>


    /* EditProfileFragment*/
    fun navigateToProfile()

    fun getProfileNavigation(): LiveData<Event<Bundle>>

    fun getEmailId(): LiveData<String>

    fun getChangePhotoEvent(): LiveData<Event<Any>>

    fun onChangePhotoClicked()

    fun uploadFile(image: MultipartBody.Part)

    fun getNameValidation(): LiveData<Resource<Int>>

    fun getBioValidation(): LiveData<Resource<Int>>

    fun getNameField(): MutableLiveData<String>

    fun getBioField(): MutableLiveData<String>

    fun onSaveProfile()

    fun validateInputs(s: Editable)
}