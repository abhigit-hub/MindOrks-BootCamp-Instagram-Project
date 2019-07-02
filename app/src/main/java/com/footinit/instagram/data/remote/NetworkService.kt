package com.footinit.instagram.data.remote

import com.footinit.instagram.data.remote.request.*
import com.footinit.instagram.data.remote.response.*
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @POST(Endpoints.DUMMY)
    @Headers(RequestHeaders.Key.AUTH_PUBLIC)
    fun doDummyCall(@Body request: DummyRequest): Single<DummyResponse>

    @POST(Endpoints.REFRESH_TOKEN)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    fun doRefreshTokenCall(@Body request: RefreshTokenRequest): Call<RefreshTokenResponse>

    @POST(Endpoints.LOGIN)
    @Headers(RequestHeaders.Key.AUTH_PUBLIC)
    fun doLoginCall(@Body request: LoginRequest): Single<LoginResponse>

    @POST(Endpoints.SIGNUP)
    @Headers(RequestHeaders.Key.AUTH_PUBLIC)
    fun doSignUpCall(@Body request: SignUpRequest): Single<SignUpResponse>

    @GET(Endpoints.FETCH_MY_INFO)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    fun doFetchMyInfoCall(): Single<FetchMyInfoResponse>

    @Multipart
    @POST(Endpoints.IMAGE_UPLOAD)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    fun doImageUpload(@Part image: MultipartBody.Part): Single<ImageUploadResponse>

    @PUT(Endpoints.UPDATE_MY_INFO)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    fun doUpdateMyInfoCall(@Body request: UpdateMyInfoRequest): Single<GenericResponse>

    @GET(Endpoints.FETCH_MY_POSTS)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    fun doFetchMyPostsCall(): Single<MultiplePostResponse>

    @GET(Endpoints.FETCH_ALL_POSTS)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    fun doFetchAllPostsApiCall(
        @Query("firstPostId") firstPostId: String?,
        @Query("lastPostId") lastPostId: String?
    ): Single<MultiplePostResponse>

    @PUT(Endpoints.LIKE_POST)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    fun doLikePost(@Body request: LikeRequest): Single<GenericResponse>

    @PUT(Endpoints.UNLIKE_POST)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    fun doUnlikePost(@Body request: LikeRequest): Single<GenericResponse>

    @POST(Endpoints.CREATE_POST)
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    fun doCreatePost(@Body request: CreatePostRequest): Single<SinglePostResponse>

    @GET(Endpoints.POST_DETAILS + "/{postId}")
    @Headers(RequestHeaders.Key.AUTH_PROTECTED)
    fun doGetPostDetails(@Path("postId") postId: String): Single<SinglePostResponse>
}