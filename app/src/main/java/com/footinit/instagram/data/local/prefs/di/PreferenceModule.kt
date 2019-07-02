package com.footinit.instagram.data.local.prefs.di

import android.content.Context
import android.content.SharedPreferences
import com.footinit.instagram.data.local.prefs.user.UserPreferences
import com.footinit.instagram.data.local.prefs.user.UserPreferencesImpl
import com.footinit.instagram.di.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferenceModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
        @PrefsInfo prefsName: String
    ): SharedPreferences =
        context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    @Provides
    fun provideUserPreferences(userPreferencesImpl: UserPreferencesImpl): UserPreferences = userPreferencesImpl
}