package com.footinit.instagram.data.local.prefs.user

import android.content.SharedPreferences
import com.footinit.instagram.data.local.prefs.get
import com.footinit.instagram.data.local.prefs.put
import com.footinit.instagram.data.local.prefs.remove
import com.footinit.instagram.utils.common.Constants
import com.footinit.instagram.utils.common.checkAndCastNullValue
import javax.inject.Inject


class UserPreferencesImpl @Inject constructor(val prefs: SharedPreferences) : UserPreferences {

    companion object {
        const val KEY_USER_ID = "PREF_KEY_USER_ID"
        const val KEY_USER_NAME = "PREF_KEY_USER_NAME"
        const val KEY_USER_EMAIL = "PREF_KEY_USER_EMAIL"
        const val KEY_USER_PROFILE_PIC_URL = "KEY_USER_PROFILE_PIC_URL"
        const val KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
        const val KEY_REFRESH_TOKEN = "PREF_KEY_REFRESH_TOKEN"
        const val KEY_PROFILE_INFO_SETUP = "PREF_KEY_PROFILE_INFO_SETUP"
    }

    override fun getUserId(): String? = prefs.get(KEY_USER_ID, Constants.NULL_STRING).checkAndCastNullValue()

    override fun setUserId(userId: String) = prefs.put(KEY_USER_ID, userId)

    override fun removeUserId() = prefs.remove(KEY_USER_ID)

    override fun getUserName(): String? = prefs.get(KEY_USER_NAME, Constants.NULL_STRING).checkAndCastNullValue()

    override fun setUserName(userName: String) = prefs.put(KEY_USER_NAME, userName)

    override fun removeUserName() = prefs.remove(KEY_USER_NAME)

    override fun getUserEmail(): String? = prefs.get(KEY_USER_EMAIL, Constants.NULL_STRING).checkAndCastNullValue()

    override fun setUserEmail(email: String) = prefs.put(KEY_USER_EMAIL, email)

    override fun removeUserEmail() = prefs.remove(KEY_USER_EMAIL)

    override fun getUserProfilePicUrl(): String? =
        prefs.get(KEY_USER_PROFILE_PIC_URL, Constants.NULL_STRING).checkAndCastNullValue()

    override fun setUserProfilePicUrl(profilePicUrl: String) = prefs.put(KEY_USER_PROFILE_PIC_URL, profilePicUrl)

    override fun removeUserProfilePicUrl() = prefs.remove(KEY_USER_PROFILE_PIC_URL)

    override fun getAccessToken(): String? = prefs.get(KEY_ACCESS_TOKEN, Constants.NULL_STRING).checkAndCastNullValue()

    override fun setAccessToken(token: String) = prefs.put(KEY_ACCESS_TOKEN, token)

    override fun removeAccessToken() = prefs.remove(KEY_ACCESS_TOKEN)

    override fun getRefreshToken(): String? =
        prefs.get(KEY_REFRESH_TOKEN, Constants.NULL_STRING).checkAndCastNullValue()

    override fun setRefreshToken(token: String) = prefs.put(KEY_REFRESH_TOKEN, token)

    override fun removeRefreshToken() = prefs.remove(KEY_REFRESH_TOKEN)

    override fun getProfileInfoSetupStatus(): Boolean = prefs.get(KEY_PROFILE_INFO_SETUP, false)

    override fun setProfileInfoSetupStatus(status: Boolean) = prefs.put(KEY_PROFILE_INFO_SETUP, status)
}