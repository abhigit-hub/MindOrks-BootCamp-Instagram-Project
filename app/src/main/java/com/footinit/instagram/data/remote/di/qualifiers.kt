package com.footinit.instagram.data.remote.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AccessTokenInfo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiKeyInfo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RefreshTokenInfo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserInfo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NetworkCacheSize

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NetworkCacheDirectory