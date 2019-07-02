package com.footinit.instagram.ui.main

interface Interactor {
    interface HomeInteractor {
        fun showUserLikes(postId: String, tag: String)
    }

    interface AddPhotosInteractor {
        fun showHomeFragment()
    }

    interface ProfileInteractor {
        fun showEditProfileFragment()

        fun showUserLikes(postId: String, tag: String)
    }

    interface LikeInteractor {
        fun showPreviousFragment()
    }
}