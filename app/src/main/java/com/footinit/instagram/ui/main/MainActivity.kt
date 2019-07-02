package com.footinit.instagram.ui.main

import android.os.Bundle
import android.os.StrictMode
import android.view.Menu
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.footinit.instagram.BuildConfig
import com.footinit.instagram.R
import com.footinit.instagram.databinding.ActivityMainBinding
import com.footinit.instagram.ui.base.BaseActivity
import com.footinit.instagram.ui.main.addphotos.AddPhotosFragment
import com.footinit.instagram.ui.main.home.HomeFragment
import com.footinit.instagram.ui.main.likedetail.LikeDetailFragment
import com.footinit.instagram.ui.main.profile.ProfileFragment
import com.footinit.instagram.ui.main.profile.editprofile.EditProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(),
    Interactor.HomeInteractor, Interactor.AddPhotosInteractor, Interactor.ProfileInteractor, Interactor.LikeInteractor {

    companion object {
        const val TAG = "MainActivity"
    }

    private var activeFragment: Fragment? = null
    private var menu: Menu? = null

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun setupObservers() {
        super.setupObservers()

        viewModel.getMainNavigation().observe(this, Observer {
            it?.getIfNotHandled()?.run {
                when (this) {
                    BottomMenuNavigation.HOME -> showHome()
                    BottomMenuNavigation.ADD_PHOTOS -> showAddPhotos()
                    BottomMenuNavigation.PROFILE -> showProfile()
                }
            }
        })
    }

    override fun setupView(savedInstanceState: Bundle?) {
        binding.viewModel = viewModel
        findViewById<BottomNavigationView>(R.id.bottom_navigation_view_main).itemIconTintList = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build()
            )
        }
    }

    override fun onResume() {
        super.onResume()
        setUpAllCallbacks()
    }

    override fun onPause() {
        super.onPause()
        removeAllCallBacks()
    }

    private fun setUpAllCallbacks() {
        (supportFragmentManager.findFragmentByTag(HomeFragment.TAG) as HomeFragment?)?.setCallback(this)
        (supportFragmentManager.findFragmentByTag(AddPhotosFragment.TAG) as AddPhotosFragment?)?.setCallback(this)
        (supportFragmentManager.findFragmentByTag(ProfileFragment.TAG) as ProfileFragment?)?.setCallback(this)
        (supportFragmentManager.findFragmentByTag(LikeDetailFragment.TAG) as LikeDetailFragment?)?.setCallback(this)
    }

    private fun removeAllCallBacks() {
        (supportFragmentManager.findFragmentByTag(HomeFragment.TAG) as HomeFragment?)?.removeCallback()
        (supportFragmentManager.findFragmentByTag(AddPhotosFragment.TAG) as AddPhotosFragment?)?.removeCallback()
        (supportFragmentManager.findFragmentByTag(ProfileFragment.TAG) as ProfileFragment?)?.removeCallback()
        (supportFragmentManager.findFragmentByTag(LikeDetailFragment.TAG) as LikeDetailFragment?)?.removeCallback()
    }

    private fun setVisibilityForBottomNavigationView(status: Boolean) {
        findViewById<BottomNavigationView>(R.id.bottom_navigation_view_main).visibility =
            if (status) View.VISIBLE else View.GONE
    }

    private fun showHome() {
        if (activeFragment is HomeFragment) return

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment = supportFragmentManager.findFragmentByTag(HomeFragment.TAG) as HomeFragment?

        if (fragment == null) {
            fragment = HomeFragment.newInstance()
            fragmentTransaction.add(R.id.container_main, fragment, HomeFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) fragmentTransaction.hide(activeFragment as Fragment)

        fragmentTransaction.commit()

        activeFragment = fragment

        fragment.setCallback(this)

        setVisibilityForBottomNavigationView(true)
    }

    private fun showAddPhotos() {
        if (activeFragment is AddPhotosFragment) return

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment = supportFragmentManager.findFragmentByTag(AddPhotosFragment.TAG) as AddPhotosFragment?

        if (fragment == null) {
            fragment = AddPhotosFragment.newInstance()
            fragmentTransaction.add(R.id.container_main, fragment, AddPhotosFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) fragmentTransaction.hide(activeFragment as Fragment)

        fragmentTransaction.commit()

        activeFragment = fragment

        fragment.setCallback(this)

        setVisibilityForBottomNavigationView(true)
    }

    private fun showProfile() {
        if (activeFragment is ProfileFragment) return

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment = supportFragmentManager.findFragmentByTag(ProfileFragment.TAG) as ProfileFragment?

        if (fragment == null) {
            fragment = ProfileFragment.newInstance()
            fragmentTransaction.add(R.id.container_main, fragment, ProfileFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) fragmentTransaction.hide(activeFragment as Fragment)

        fragmentTransaction.commitNow()

        activeFragment = fragment

        fragment.setCallback(this)

        setVisibilityForBottomNavigationView(true)
    }

    override fun showEditProfileFragment() {
        if (activeFragment is EditProfileFragment) return

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        val fragment = EditProfileFragment.newInstance()

        fragmentTransaction.add(R.id.container_main, fragment, EditProfileFragment.TAG)
            .addToBackStack(EditProfileFragment.TAG)

        if (activeFragment != null) fragmentTransaction.hide(activeFragment as Fragment)

        fragmentTransaction.commit()

        activeFragment = fragment

        setVisibilityForBottomNavigationView(false)
    }

    private fun showLikeDetailFragment(postId: String, previousFragmentTag: String) {
        if (activeFragment is LikeDetailFragment) return

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        val fragment = LikeDetailFragment.newInstance(postId)

        fragmentTransaction.add(R.id.container_main, fragment, LikeDetailFragment.TAG)
            .addToBackStack(previousFragmentTag)

        if (activeFragment != null) fragmentTransaction.hide(activeFragment as Fragment)

        fragmentTransaction.commit()

        activeFragment = fragment

        fragment.setCallback(this)

        setVisibilityForBottomNavigationView(false)
    }

    override fun showUserLikes(postId: String, tag: String) {
        when (tag) {
            HomeFragment.TAG -> showLikeDetailFragment(postId, HomeFragment.TAG)
            ProfileFragment.TAG -> showLikeDetailFragment(postId, ProfileFragment.TAG)
        }
    }

    override fun showPreviousFragment() {
        when (supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name) {
            HomeFragment.TAG -> showHome()
            ProfileFragment.TAG -> showProfile()
        }
    }

    override fun showHomeFragment() {
        findViewById<BottomNavigationItemView>(R.id.menu_home).performClick()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            if (activeFragment is EditProfileFragment) showProfile()
            if (activeFragment is LikeDetailFragment) showPreviousFragment()
        }
        super.onBackPressed()
    }
}