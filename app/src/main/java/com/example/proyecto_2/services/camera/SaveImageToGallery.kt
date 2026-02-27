package com.example.proyecto_2.services.camera

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.example.proyecto_2.models.camera.FileData
import java.io.File

fun SaveImgageToGallery(context: Context, image: File): FileData {
    val resolver = context.contentResolver

    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, image.name)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/MiApp")
        put(MediaStore.Images.Media.IS_PENDING, 1)
    }

    var imageUri = resolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues
    )

    imageUri?.let { uri ->
        resolver.openOutputStream(uri)?.use { outputStream ->
            image.inputStream().use { inputStream -> inputStream.copyTo(outputStream) }
        }

        contentValues.clear()
        contentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
        resolver.update(uri, contentValues, null, null)
    }


    return FileData(
        imageUri!!,
        null,
        null
    );


}

