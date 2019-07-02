package com.footinit.instagram.ui.main.likedetail

import android.os.Bundle
import androidx.lifecycle.LiveData
import com.footinit.instagram.data.local.db.entity.UserEntity
import com.footinit.instagram.ui.base.BaseViewModel
import com.footinit.instagram.utils.common.Event

interface LikeDetailViewModel : BaseViewModel {
    fun updatePostId(postId: String)

    fun getUsers(): LiveData<MutableList<UserEntity>>

    fun getUserLikeCount(): LiveData<Int>

    fun getBackNavigation(): LiveData<Event<Bundle>>

    fun onBackNavigation()
}