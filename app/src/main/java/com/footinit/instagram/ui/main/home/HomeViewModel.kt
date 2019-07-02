package com.footinit.instagram.ui.main.home

import androidx.lifecycle.LiveData
import com.footinit.instagram.data.local.db.entity.PostWithUser
import com.footinit.instagram.ui.base.BaseViewModel
import com.footinit.instagram.utils.common.LoadMoreListener

interface HomeViewModel : BaseViewModel {

    fun getIsLoading(): LiveData<Boolean>

    fun getAllPosts(): LiveData<List<PostWithUser>>

    fun getLoadMoreListener(): LoadMoreListener
}