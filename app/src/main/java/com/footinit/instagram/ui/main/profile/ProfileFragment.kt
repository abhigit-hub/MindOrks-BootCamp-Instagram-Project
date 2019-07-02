package com.footinit.instagram.ui.main.profile

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.footinit.instagram.R
import com.footinit.instagram.databinding.FragmentProfileBinding
import com.footinit.instagram.ui.base.BaseFragment
import com.footinit.instagram.ui.main.Interactor
import com.footinit.instagram.ui.main.home.posts.PostAdapter
import javax.inject.Inject

class ProfileFragment : BaseFragment<FragmentProfileBinding, SharedProfileViewModel>(), PostAdapter.FragmentCallback {

    @Inject
    lateinit var adapter: PostAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    companion object {
        val TAG = "ProfileFragment"

        fun newInstance(): ProfileFragment {
            val args = Bundle()
            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var callback: Interactor.ProfileInteractor? = null

    fun setCallback(callback: Interactor.ProfileInteractor) {
        this.callback = callback
    }

    fun removeCallback() {
        this.callback = null
    }

    override fun getLayoutId(): Int = R.layout.fragment_profile

    override fun getFragmentTag(): String = TAG

    override fun setupView(view: View) {
        binding.viewModel = viewModel
        binding.recyclerviewProfilePost.layoutManager = linearLayoutManager
        binding.recyclerviewProfilePost.adapter = adapter

        adapter.setCallback(this)
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.getEditProfileNavigation().observe(this, Observer {
            it?.getIfNotHandled()?.run { showEditFragment() }
        })

        viewModel.getProfileInfo().observe(this, Observer {})

        viewModel.getMyPosts().observe(this, Observer {
            it?.run { adapter.appendData(it) }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.setCallback(this)
    }

    override fun onDestroy() {
        adapter.removeCallback()
        super.onDestroy()
    }

    private fun showEditFragment() {
        callback?.showEditProfileFragment()
    }

    override fun showUserLikes(postId: String) {
        callback?.showUserLikes(postId, TAG)
    }
}