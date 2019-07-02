package com.footinit.instagram.ui.main.likedetail

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.footinit.instagram.R
import com.footinit.instagram.databinding.FragmentLikeDetailsBinding
import com.footinit.instagram.ui.base.BaseFragment
import com.footinit.instagram.ui.main.Interactor
import com.footinit.instagram.ui.main.likedetail.likes.LikeAdapter
import javax.inject.Inject

class LikeDetailFragment : BaseFragment<FragmentLikeDetailsBinding, LikeDetailViewModel>() {

    @Inject
    lateinit var adapter: LikeAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    private var callback: Interactor.LikeInteractor? = null

    fun setCallback(callback: Interactor.LikeInteractor) {
        this.callback = callback
    }

    fun removeCallback() {
        this.callback = null
    }

    companion object {
        val TAG = "LikeDetailFragment"

        val KEY_POST_ID = "KEY_POST_ID"

        fun newInstance(postId: String): LikeDetailFragment {
            val args = Bundle()
            args.putString(KEY_POST_ID, postId)
            val fragment = LikeDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_like_details

    override fun getFragmentTag(): String = TAG

    override fun setupView(view: View) {
        arguments?.getString(KEY_POST_ID)?.let {
            if (it.isNotEmpty())
                viewModel.updatePostId(it)
        }

        binding.viewModel = viewModel
        binding.recyclerviewLikedetails.layoutManager = linearLayoutManager
        binding.recyclerviewLikedetails.adapter = adapter
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.getUsers().observe(this, Observer {
            it?.run { adapter.appendData(this) }
        })

        viewModel.getBackNavigation().observe(this, Observer {
            it?.getIfNotHandled()?.run { showPreviousFragment() }
        })
    }

    private fun showPreviousFragment() {
        callback?.showPreviousFragment()
    }
}