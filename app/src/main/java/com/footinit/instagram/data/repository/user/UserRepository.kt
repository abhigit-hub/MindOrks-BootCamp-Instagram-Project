package com.footinit.instagram.data.repository.user

import com.footinit.instagram.data.model.UserSession
import io.reactivex.Single

interface UserRepository {

    fun saveCurrentUserSession(userSession: UserSession)

    fun removeCurrentUserSession()

    fun getCurrentUserSession(): UserSession?

    fun doUserLogin(email: String, password: String): Single<UserSession>

    fun doUserSignUp(name: String, email: String, password: String): Single<UserSession>
}