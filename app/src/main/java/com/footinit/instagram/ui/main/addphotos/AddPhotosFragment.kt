package com.footinit.instagram.ui.main.addphotos

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.footinit.instagram.BuildConfig
import com.footinit.instagram.R
import com.footinit.instagram.databinding.FragmentAddPhotoBinding
import com.footinit.instagram.ui.base.BaseFragment
import com.footinit.instagram.ui.main.Interactor
import com.footinit.instagram.ui.main.addphotos.gallery.GalleryAdapter
import com.footinit.instagram.utils.common.FileUtils
import com.footinit.instagram.utils.display.SizeFromImage
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.IOException
import javax.inject.Inject

class AddPhotosFragment : BaseFragment<FragmentAddPhotoBinding, AddPhotosViewModel>(), GalleryAdapter.FragmentCallback {

    @Inject
    lateinit var adapter: GalleryAdapter

    @Inject
    lateinit var gridLayoutManager: GridLayoutManager

    companion object {
        val TAG = "AddPhotosFragment"
        val REQUEST_TAKE_PHOTO = 1

        fun newInstance(): AddPhotosFragment {
            val args = Bundle()
            val fragment = AddPhotosFragment()
            fragment.arguments = args
            return fragment
        }
    }

    lateinit var currentPhotoPath: String
    private var callback: Interactor.AddPhotosInteractor? = null

    fun setCallback(callback: Interactor.AddPhotosInteractor) {
        this.callback = callback
    }

    fun removeCallback() {
        this.callback = null
    }

    override fun getLayoutId(): Int = R.layout.fragment_add_photo

    override fun getFragmentTag(): String = TAG

    override fun setupView(view: View) {
        binding.viewModel = viewModel
        binding.recyclerviewAddphotoGallery.layoutManager = gridLayoutManager
        binding.recyclerviewAddphotoGallery.adapter = adapter
    }

    override fun setupObservers() {
        super.setupObservers()
        viewModel.getImagePathList().observe(this, Observer {
            it?.run { adapter.appendData(this) }
        })

        viewModel.getBackNavigation().observe(this, Observer {
            it?.getIfNotHandled()?.run { showHomeFragment() }
        })
    }

    private fun showHomeFragment() {
        callback?.showHomeFragment()
    }

    override fun onStart() {
        super.onStart()
        checkRequiredPermission()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden)
            checkRequiredPermission()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.setCallback(this)
    }

    override fun onDestroy() {
        adapter.removeCallback()
        super.onDestroy()
    }

    private fun checkRequiredPermission() {
        Dexter.withActivity(activity)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report != null) {
                        if (report.areAllPermissionsGranted()) {
                            viewModel.readImagesFromGallery()
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

    override fun onCameraModeSelected() {
        dispatchTakePictureIntent()
    }

    private fun dispatchTakePictureIntent() {
        context?.let {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(activity!!.packageManager)?.also { componentName ->
                    val photoFile: File? = try {
                        FileUtils.createImageFile()
                    } catch (e: IOException) {
                        null
                    }

                    photoFile?.also { file ->
                        currentPhotoPath = file.absolutePath
                        val photoUri: Uri = FileProvider.getUriForFile(
                            it,
                            BuildConfig.APPLICATION_ID + ".provider",
                            file
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                    }
                }
            }
        }
    }

    override fun onImageSelected(path: String) {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(getString(R.string.main_addphoto_image_confirmation))
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    proceedWithSelection(path)
                }
                .setNegativeButton(android.R.string.cancel) { _, _ ->
                    //Do Nothing
                }
                .create()
                .show()
        }

    }

    private fun proceedWithSelection(path: String) {
        val file = File(path)
        val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        MultipartBody.Part.createFormData("image", file.name, requestBody)
            ?.let {
                val size = SizeFromImage(path)
                val imageResolution = Pair(size.width(), size.height())
                viewModel.onCreatePost(it, imageResolution)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK)
            when (requestCode) {
                REQUEST_TAKE_PHOTO -> {
                    proceedWithSelection(currentPhotoPath)
                }
            }
    }
}