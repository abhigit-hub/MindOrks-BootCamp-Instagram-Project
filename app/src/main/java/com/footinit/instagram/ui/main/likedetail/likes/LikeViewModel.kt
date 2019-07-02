package com.footinit.instagram.ui.main.likedetail.likes

import com.footinit.instagram.data.local.db.entity.UserEntity
import com.footinit.instagram.data.model.UserSession
import com.footinit.instagram.data.remote.RequestHeaders
import com.footinit.instagram.data.repository.user.UserRepository
import com.footinit.instagram.ui.base.BaseItemViewModel
import com.footinit.instagram.ui.di.ViewModelScope
import com.footinit.instagram.utils.network.NetworkHelper
import javax.inject.Inject

@ViewModelScope
class LikeViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    userRepository: UserRepository,
    requestHeaders: RequestHeaders
) : BaseItemViewModel<UserEntity>(networkHelper) {

    private var userEntity: UserEntity? = null
    private val currentUserSession: UserSession? = userRepository.getCurrentUserSession()

    private val headers: Map<String, String> = mapOf(
        Pair(RequestHeaders.Param.API_KEY.toString(), requestHeaders.apiKey),
        Pair(RequestHeaders.Param.USER_ID.toString(), currentUserSession!!.id),
        Pair(RequestHeaders.Param.ACCESS_TOKEN.toString(), currentUserSession.accessToken)
    )

    fun getUserName(): String = userEntity?.name?.toLowerCase()?.replace(" ", "_")
        ?.replace(".", "_") ?: ""

    fun getUserId(): String = userEntity?.name ?: ""
    fun getProfilePicUrl(): String? = userEntity?.profilePicUrl
    fun getHeaders() = headers

    override fun setUp() {
        userEntity = data
    }
}