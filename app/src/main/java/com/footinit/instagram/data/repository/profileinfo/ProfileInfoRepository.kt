package com.footinit.instagram.data.repository.profileinfo

import androidx.lifecycle.LiveData
import com.footinit.instagram.data.local.db.entity.Profile
import com.footinit.instagram.data.remote.request.UpdateMyInfoRequest
import com.footinit.instagram.data.remote.response.FetchMyInfoResponse
import com.footinit.instagram.data.remote.response.GenericResponse
import io.reactivex.Single
import okhttp3.MultipartBody

interface ProfileInfoRepository {
    fun doFetchMyInfo(): Single<FetchMyInfoResponse>

    fun fetchProfileInfo(): LiveData<Profile>

    fun fetchProfileInfoSetupStatus(): Boolean

    fun saveProfileInfo(profile: Profile): Long

    fun updateProfileInfo(profile: Profile): Int

    fun doImageUpload(image: MultipartBody.Part): Single<String>

    fun doUpdateMyInfo(request: UpdateMyInfoRequest): Single<GenericResponse>
}