package com.footinit.instagram.utils.common

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.footinit.instagram.di.injector.ApplicationInjector
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


object FileUtils {

    const val MAX_QUERY_SIZE = 10

    val TAG = FileUtils::class.java.simpleName

    fun getRealPathFromURI(context: Context, contentUri: Uri): String? {
        var cursor: Cursor? = null
        return if (contentUri.scheme != null && contentUri.scheme == "file") {
            contentUri.path
        } else {
            try {
                val projection = arrayOf(MediaStore.Images.Media.DATA)
                cursor = context.contentResolver.query(contentUri, projection, null, null, null)
                val columnIndex = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                cursor.getString(columnIndex)
            } finally {
                cursor?.close()
            }
        }
    }

    fun getGalleryImageCount(): Int {
        val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)
        val cursor = ApplicationInjector.applicationComponent.getApplication().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
            null,
            null,
            null
        )

        val size = cursor?.count ?: 0
        cursor?.close()
        return size
    }

    fun fetchImagesFromGallery(index: Int): MutableList<String> {
        val buildList: MutableList<String> = mutableListOf()

        val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID)
        val orderBy = MediaStore.Images.Media.DATE_TAKEN

        val cursor = ApplicationInjector.applicationComponent.getApplication().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
            null,
            null,
            "$orderBy DESC"
        )

        if (cursor != null) {
            var count = 0
            if (index > 0) cursor.moveToPosition(index)

            while (cursor.moveToNext() && count < MAX_QUERY_SIZE) {
                val dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                cursor.getString(dataColumnIndex)?.let { buildList.add(it) }
                count++
            }

            cursor.close()
        }
        return buildList
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        // File Name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }
}