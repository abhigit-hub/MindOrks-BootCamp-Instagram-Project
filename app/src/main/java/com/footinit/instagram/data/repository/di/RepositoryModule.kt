package com.footinit.instagram.data.repository.di

import com.footinit.instagram.data.repository.dummy.DummyRepository
import com.footinit.instagram.data.repository.dummy.DummyRepositoryImpl
import com.footinit.instagram.data.repository.post.PostRepository
import com.footinit.instagram.data.repository.post.PostRepositoryImpl
import com.footinit.instagram.data.repository.profileinfo.ProfileInfoRepository
import com.footinit.instagram.data.repository.profileinfo.ProfileInfoRepositoryImpl
import com.footinit.instagram.data.repository.user.UserRepository
import com.footinit.instagram.data.repository.user.UserRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository =
        userRepositoryImpl

    @Provides
    fun provideDummyRepository(dummyRepositoryImpl: DummyRepositoryImpl): DummyRepository =
        dummyRepositoryImpl

    @Provides
    fun provideProfileInfoRepository(profileInfoRepositoryImpl: ProfileInfoRepositoryImpl): ProfileInfoRepository =
        profileInfoRepositoryImpl

    @Provides
    fun providePostRepository(postRepositoryImpl: PostRepositoryImpl): PostRepository =
        postRepositoryImpl
}