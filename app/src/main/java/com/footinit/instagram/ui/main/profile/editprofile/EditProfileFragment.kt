package com.footinit.instagram.ui.main.profile.editprofile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.footinit.instagram.R
import com.footinit.instagram.databinding.FragmentEditProfileBinding
import com.footinit.instagram.ui.base.BaseFragment
import com.footinit.instagram.ui.main.profile.SharedProfileViewModel
import com.footinit.instagram.utils.common.FileUtils
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class EditProfileFragment : BaseFragment<FragmentEditProfileBinding, SharedProfileViewModel>() {

    companion object {
        val TAG = "EditProfileFragment"

        fun newInstance(): EditProfileFragment {
            val args = Bundle()
            val fragment = EditProfileFragment()
            fragment.arguments = args
            return fragment
        }

        const val SELECTION_TYPE_GALLERY = 1
    }

    override fun getLayoutId(): Int = R.layout.fragment_edit_profile

    override fun getFragmentTag(): String = TAG

    override fun setupView(view: View) {
        binding.viewModel = viewModel
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.getProfileNavigation().observe(this, Observer {
            it?.getIfNotHandled()?.let { goBack() }
        })

        viewModel.getChangePhotoEvent().observe(this, Observer {
            it?.getIfNotHandled()?.let { pickFromGallery() }
        })

        viewModel.getProfileInfo().observe(this, Observer {})
    }

    private fun pickFromGallery() {
        Dexter.withActivity(activity)
            .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report != null) {
                        if (report.areAllPermissionsGranted()) {
                            val galleryIntent = Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            )
                            startActivityForResult(galleryIntent, SELECTION_TYPE_GALLERY)
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    token?.continuePermissionRequest()
                }
            }).check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECTION_TYPE_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            data.data?.let { selectedImageUri ->
                val file = File(context?.let { FileUtils.getRealPathFromURI(it, selectedImageUri) })
                val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
                MultipartBody.Part.createFormData("image", file.name, requestBody)
                    ?.let { viewModel.uploadFile(it) }
            }
        }
    }
}