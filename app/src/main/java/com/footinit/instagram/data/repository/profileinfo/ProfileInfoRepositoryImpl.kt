package com.footinit.instagram.data.repository.profileinfo

import androidx.lifecycle.LiveData
import com.footinit.instagram.data.local.db.DatabaseService
import com.footinit.instagram.data.local.db.entity.Profile
import com.footinit.instagram.data.local.prefs.user.UserPreferences
import com.footinit.instagram.data.remote.NetworkService
import com.footinit.instagram.data.remote.request.UpdateMyInfoRequest
import com.footinit.instagram.data.remote.response.FetchMyInfoResponse
import com.footinit.instagram.data.remote.response.GenericResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import javax.inject.Inject

class ProfileInfoRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    private val userPreferences: UserPreferences
) : ProfileInfoRepository {

    override fun doFetchMyInfo(): Single<FetchMyInfoResponse> = networkService.doFetchMyInfoCall()

    override fun fetchProfileInfo(): LiveData<Profile> = databaseService.profileInfoDao().getProfileInfo()

    override fun fetchProfileInfoSetupStatus(): Boolean = userPreferences.getProfileInfoSetupStatus()

    override fun saveProfileInfo(profile: Profile): Long {
        profile.profilePicUrl?.let { userPreferences.setUserProfilePicUrl(it) }
        profile.email = userPreferences.getUserEmail()
        databaseService.profileInfoDao().insert(profile).let {
            if (it > 0) userPreferences.setProfileInfoSetupStatus(true)
            return it
        }
    }

    override fun updateProfileInfo(profile: Profile): Int {
        profile.profilePicUrl?.let { userPreferences.setUserProfilePicUrl(it) }
        return databaseService.profileInfoDao().updateProfile(profile)
    }

    override fun doImageUpload(image: MultipartBody.Part): Single<String> =
        networkService.doImageUpload(image)
            .map {
                it.data.imageUrl
            }

    override fun doUpdateMyInfo(request: UpdateMyInfoRequest): Single<GenericResponse> =
        networkService.doUpdateMyInfoCall(request)
}