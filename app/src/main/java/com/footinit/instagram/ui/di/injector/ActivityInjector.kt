package com.footinit.instagram.ui.di.injector

import com.footinit.instagram.di.injector.ApplicationInjector
import com.footinit.instagram.ui.base.BaseActivity
import com.footinit.instagram.ui.di.component.DaggerLoginActivityComponent
import com.footinit.instagram.ui.di.component.DaggerMainActivityComponent
import com.footinit.instagram.ui.di.component.DaggerSignUpActivityComponent
import com.footinit.instagram.ui.di.component.DaggerSplashActivityComponent
import com.footinit.instagram.ui.di.module.LoginActivityModule
import com.footinit.instagram.ui.di.module.MainActivityModule
import com.footinit.instagram.ui.di.module.SignUpActivityModule
import com.footinit.instagram.ui.di.module.SplashActivityModule
import com.footinit.instagram.ui.login.LoginActivity
import com.footinit.instagram.ui.main.MainActivity
import com.footinit.instagram.ui.signup.SignUpActivity
import com.footinit.instagram.ui.splash.SplashActivity

object ActivityInjector {

    fun <A : BaseActivity<*, *>> inject(activity: A) {
        when (activity) {
            is SplashActivity -> DaggerSplashActivityComponent
                .builder()
                .applicationComponent(ApplicationInjector.applicationComponent)
                .splashActivityModule(SplashActivityModule(activity))
                .build()
                .inject(activity)
            is LoginActivity -> DaggerLoginActivityComponent
                .builder()
                .applicationComponent(ApplicationInjector.applicationComponent)
                .loginActivityModule(LoginActivityModule(activity))
                .build()
                .inject(activity)
            is SignUpActivity -> DaggerSignUpActivityComponent
                .builder()
                .applicationComponent(ApplicationInjector.applicationComponent)
                .signUpActivityModule(SignUpActivityModule(activity))
                .build()
                .inject(activity)
            is MainActivity -> DaggerMainActivityComponent
                .builder()
                .applicationComponent(ApplicationInjector.applicationComponent)
                .mainActivityModule(MainActivityModule(activity))
                .build()
                .inject(activity)
        }
    }
}