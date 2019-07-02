package com.footinit.instagram.data.repository.user

import com.footinit.instagram.data.local.db.DatabaseService
import com.footinit.instagram.data.local.prefs.user.UserPreferences
import com.footinit.instagram.data.model.UserSession
import com.footinit.instagram.data.remote.NetworkService
import com.footinit.instagram.data.remote.request.LoginRequest
import com.footinit.instagram.data.remote.request.SignUpRequest
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    private val userPreferences: UserPreferences
) : UserRepository {

    override fun saveCurrentUserSession(userSession: UserSession) {
        if (!userSession.id.isBlank()) userPreferences.setUserId(userSession.id)
        if (!userSession.name.isBlank()) userPreferences.setUserName(userSession.name)
        if (!userSession.email.isBlank()) userPreferences.setUserEmail(userSession.email)
        if (!userSession.accessToken.isBlank()) userPreferences.setAccessToken(userSession.accessToken)
        if (!userSession.refreshToken.isBlank()) userPreferences.setRefreshToken(userSession.refreshToken)
        if (!userSession.profilePicUrl.isNullOrBlank()) userPreferences.setUserProfilePicUrl(userSession.profilePicUrl)
    }

    override fun removeCurrentUserSession() {
        userPreferences.removeUserId()
        userPreferences.removeUserName()
        userPreferences.removeUserEmail()
        userPreferences.removeAccessToken()
        userPreferences.removeRefreshToken()
        userPreferences.removeUserProfilePicUrl()
    }

    override fun getCurrentUserSession(): UserSession? {

        val userId = userPreferences.getUserId()
        val userName = userPreferences.getUserName()
        val userEmail = userPreferences.getUserEmail()
        val accessToken = userPreferences.getAccessToken()
        val refreshToken = userPreferences.getRefreshToken()
        val profilePicUrl = userPreferences.getUserProfilePicUrl()

        return if (
            userId !== null
            && userName != null
            && userEmail != null
            && accessToken != null
            && refreshToken != null
        ) UserSession(userId, userName, userEmail, accessToken, refreshToken, profilePicUrl)
        else null
    }

    override fun doUserLogin(email: String, password: String): Single<UserSession> =
        networkService.doLoginCall(LoginRequest(email, password))
            .map {
                UserSession(
                    it.userId,
                    it.userName,
                    it.userEmail,
                    it.accessToken,
                    it.refreshToken
                )
            }

    override fun doUserSignUp(name: String, email: String, password: String): Single<UserSession> =
        networkService.doSignUpCall(SignUpRequest(name, email, password))
            .map {
                UserSession(
                    it.userId,
                    it.userName,
                    it.userEmail,
                    it.accessToken,
                    it.refreshToken
                )
            }
}