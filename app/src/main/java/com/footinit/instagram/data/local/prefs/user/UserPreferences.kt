package com.footinit.instagram.data.local.prefs.user

import javax.inject.Singleton

@Singleton
interface UserPreferences {

    fun getUserId(): String?

    fun setUserId(userId: String)

    fun removeUserId()

    fun getUserName(): String?

    fun setUserName(userName: String)

    fun removeUserName()

    fun getUserEmail(): String?

    fun setUserEmail(email: String)

    fun removeUserEmail()

    fun getUserProfilePicUrl(): String?

    fun setUserProfilePicUrl(profilePicUrl: String)

    fun removeUserProfilePicUrl()

    fun getAccessToken(): String?

    fun setAccessToken(token: String)

    fun removeAccessToken()

    fun getRefreshToken(): String?

    fun setRefreshToken(token: String)

    fun removeRefreshToken()

    fun getProfileInfoSetupStatus(): Boolean

    fun setProfileInfoSetupStatus(status: Boolean)
}