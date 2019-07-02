package com.footinit.instagram.ui.main.profile

import android.os.Bundle
import android.text.Editable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.footinit.instagram.R
import com.footinit.instagram.data.local.db.entity.PostWithUser
import com.footinit.instagram.data.local.db.entity.Profile
import com.footinit.instagram.data.model.UserSession
import com.footinit.instagram.data.remote.RequestHeaders
import com.footinit.instagram.data.remote.request.UpdateMyInfoRequest
import com.footinit.instagram.data.repository.post.PostRepository
import com.footinit.instagram.data.repository.profileinfo.ProfileInfoRepository
import com.footinit.instagram.data.repository.user.UserRepository
import com.footinit.instagram.ui.base.BaseViewModelImpl
import com.footinit.instagram.utils.common.*
import com.footinit.instagram.utils.network.NetworkHelper
import com.footinit.instagram.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MultipartBody

class SharedProfileViewModelImpl(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val userRepository: UserRepository,
    private val profileInfoRepository: ProfileInfoRepository,
    private val postRepository: PostRepository,
    requestHeaders: RequestHeaders
) : BaseViewModelImpl(schedulerProvider, compositeDisposable, networkHelper), SharedProfileViewModel {

    private var currentProfile: Profile? = null
    private var currentUserSession: UserSession? = userRepository.getCurrentUserSession()

    private val headers = mapOf(
        Pair(RequestHeaders.Param.API_KEY.value, requestHeaders.apiKey),
        Pair(RequestHeaders.Param.USER_ID.value, currentUserSession!!.id),
        Pair(RequestHeaders.Param.ACCESS_TOKEN.value, currentUserSession!!.accessToken)
    )

    private val posts: LiveData<List<PostWithUser>> = postRepository.getAllMyPosts()
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData()
    private val name: MutableLiveData<String> = MutableLiveData()
    private val emailId: MutableLiveData<String> = MutableLiveData()
    private val profilePicUrl: MutableLiveData<String> = MutableLiveData()
    private val tagline: MutableLiveData<String> = MutableLiveData()
    private val profile: MediatorLiveData<Profile> = MediatorLiveData()
    private val editProfileNavigation: MutableLiveData<Event<Bundle>> = MutableLiveData()
    private val profileNavigation: MutableLiveData<Event<Bundle>> = MutableLiveData()
    private val changePhotoEvent: MutableLiveData<Event<Any>> = MutableLiveData()
    private val validationList: MutableLiveData<List<Validation>> = MutableLiveData()
    private val nameField: MutableLiveData<String> = MutableLiveData()
    private val bioField: MutableLiveData<String> = MutableLiveData()
    private val postCount: LiveData<Int> = Transformations.map(posts) { it?.size }

    override fun getIsLoading(): LiveData<Boolean> = isLoading
    override fun getProfileName(): LiveData<String> = name
    override fun getProfilePicUrl(): LiveData<String> = profilePicUrl
    override fun getProfileTagline(): LiveData<String> = tagline
    override fun getProfileInfo(): LiveData<Profile> = profile
    override fun getEmailId(): LiveData<String> = emailId
    override fun getPostCount(): LiveData<Int> = postCount
    override fun getMyPosts(): LiveData<List<PostWithUser>> = posts
    override fun getHeaders(): Map<String, String> = headers
    override fun getChangePhotoEvent(): LiveData<Event<Any>> = changePhotoEvent
    override fun getProfileNavigation(): LiveData<Event<Bundle>> = profileNavigation
    override fun getEditProfileNavigation(): LiveData<Event<Bundle>> = editProfileNavigation
    override fun getNameValidation(): LiveData<Resource<Int>> = transformValidation(Validation.Field.NAME)
    override fun getBioValidation(): LiveData<Resource<Int>> = transformValidation(Validation.Field.BIO)
    override fun getNameField(): MutableLiveData<String> = nameField
    override fun getBioField(): MutableLiveData<String> = bioField

    override fun navigateToEditProfile() = editProfileNavigation.postValue(Event(Bundle()))
    override fun navigateToProfile() = profileNavigation.postValue(Event(Bundle()))
    override fun onChangePhotoClicked() = changePhotoEvent.postValue(Event(Any()))

    private fun transformValidation(field: Validation.Field) =
        Transformations.map(validationList) {
            it.find { validation -> validation.field == field }
                ?.run { return@run this.resource }
                ?: Resource.unknown()
        }

    init {
        if (!profileInfoRepository.fetchProfileInfoSetupStatus()) {
            makeFetchMyInfoApiCall()
        }

        makeFetchMyPostsApiCall()

        profile.addSource(profileInfoRepository.fetchProfileInfo()) { profileInfo ->
            profileInfo?.let { profile ->
                currentProfile = profileInfo
                profile.name?.let {
                    name.postValue(it)
                    nameField.postValue(it)
                }
                profile.email?.let { emailId.postValue(it) }
                profile.profilePicUrl?.let { profilePicUrl.postValue(it) }
                profile.tagline?.let {
                    tagline.postValue(it)
                    bioField.postValue(it)
                }
            }
        }
    }

    override fun validateInputs(s: Editable) {
        val name = nameField.value
        val bio = bioField.value

        val validations = Validator.validateProfileFields(name, bio)
        validationList.postValue(validations)
    }

    override fun onViewCreated() {}

    override fun uploadFile(image: MultipartBody.Part) {
        if (checkInternetConnectionWithMessage()) {
            isLoading.postValue(true)
            compositeDisposable.addAll(
                profileInfoRepository.doImageUpload(image)
                    .doOnError { messageStringIdLiveData.postValue(Resource.error(R.string.all_something_went_wrong)) }
                    .flatMap { imageUrl ->
                        profileInfoRepository.doUpdateMyInfo(UpdateMyInfoRequest("", imageUrl, ""))
                            .doOnSuccess { response ->
                                isLoading.postValue(false)
                                currentProfile?.apply {
                                    this.profilePicUrl = imageUrl
                                    profileInfoRepository.updateProfileInfo(this)
                                }
                            }
                    }
                    .subscribeOn(schedulerProvider.io())
                    .subscribe(
                        {

                        },
                        {
                            handleNetworkError(it)
                            isLoading.postValue(false)
                        }
                    )
            )
        }
    }

    override fun onSaveProfile() {
        val name = nameField.value
        val bio = bioField.value

        val validationList = Validator.validateProfileFields(name, bio)

        if (validationList.isNotEmpty() && name != null && bio != null) {
            val successValidation = validationList.filter { it.resource.status == Status.SUCCESS }

            if (successValidation.size == validationList.size && checkInternetConnectionWithMessage()) {
                isLoading.postValue(true)

                compositeDisposable.addAll(
                    profileInfoRepository.doUpdateMyInfo(UpdateMyInfoRequest(name, "", bio))
                        .subscribeOn(schedulerProvider.io())
                        .subscribe(
                            { response ->
                                isLoading.postValue(false)
                                messageLiveData.postValue(Resource.success(response.message))
                                currentProfile?.apply {
                                    this.name = name
                                    this.tagline = bio
                                    profileInfoRepository.updateProfileInfo(this)
                                }
                                userRepository.saveCurrentUserSession(UserSession(name = name))
                                profileNavigation.postValue(Event(Bundle()))
                            },
                            {
                                handleNetworkError(it)
                                isLoading.postValue(false)
                            }
                        )
                )
            }
        }
    }

    private fun makeFetchMyInfoApiCall() {
        if (checkInternetConnectionWithMessage()) {
            isLoading.postValue(true)
            compositeDisposable.addAll(
                profileInfoRepository.doFetchMyInfo()
                    .subscribeOn(schedulerProvider.io())
                    .subscribe(
                        { response ->
                            isLoading.postValue(false)
                            response?.data?.let { profileInfoRepository.saveProfileInfo(it) }
                        },
                        {
                            handleNetworkError(it)
                            isLoading.postValue(false)
                        }
                    )
            )
        }
    }

    private fun makeFetchMyPostsApiCall() {
        if (checkInternetConnectionWithMessage()) {
            isLoading.postValue(true)
            compositeDisposable.addAll(
                postRepository.doFetchAndSaveAllMyPosts()
                    .subscribeOn(schedulerProvider.io())
                    .subscribe(
                        {

                        },
                        {
                            isLoading.postValue(false)
                            handleNetworkError(it)
                        },
                        {
                            isLoading.postValue(false)
                        }
                    )
            )
        }
    }
}
