package com.footinit.instagram.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.footinit.instagram.R
import com.footinit.instagram.databinding.FragmentHomeBinding
import com.footinit.instagram.ui.base.BaseFragment
import com.footinit.instagram.ui.main.Interactor
import com.footinit.instagram.ui.main.home.posts.PostAdapter
import javax.inject.Inject

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(), PostAdapter.FragmentCallback {

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var adapter: PostAdapter

    companion object {
        val TAG = "HomeFragment"

        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var callback: Interactor.HomeInteractor? = null

    fun setCallback(callback: Interactor.HomeInteractor) {
        this.callback = callback
    }

    fun removeCallback() {
        this.callback = null
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun getFragmentTag(): String = TAG

    override fun setupView(view: View) {
        binding.viewModel = viewModel
        binding.recyclerviewHomePost.layoutManager = linearLayoutManager
        binding.recyclerviewHomePost.adapter = adapter

        adapter.setCallback(this)
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.getAllPosts().observe(this, Observer {
            it?.run { adapter.appendData(this) }
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

    override fun showUserLikes(postId: String) {
        callback?.showUserLikes(postId, TAG)
    }
}