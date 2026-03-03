package com.example.proyecto_2.services.camera

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.example.proyecto_2.models.camera.FileData
import java.io.File

fun SaveImgageToGallery(context: Context, file: File): FileData {

    val resolver = context.contentResolver

    val isVideo = file.extension.lowercase() in listOf("mp4", "mov", "mkv")

    val collection = if (isVideo) {
        MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    } else {
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }

    val mimeType = if (isVideo) {
        "video/${file.extension.lowercase()}"
    } else {
        "image/${file.extension.lowercase()}"
    }

    val relativePath = if (isVideo) {
        Environment.DIRECTORY_MOVIES + "/MiApp"
    } else {
        Environment.DIRECTORY_PICTURES + "/MiApp"
    }

    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, file.name)
        put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
        put(MediaStore.MediaColumns.RELATIVE_PATH, relativePath)
        put(MediaStore.MediaColumns.IS_PENDING, 1)
    }

    val fileUri = resolver.insert(collection, contentValues)
        ?: throw Exception("No se pudo crear el URI")

    resolver.openOutputStream(fileUri)?.use { outputStream ->
        file.inputStream().use { inputStream ->
            inputStream.copyTo(outputStream)
        }
    }

    contentValues.clear()
    contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
    resolver.update(fileUri, contentValues, null, null)

    return FileData(fileUri, null, null)
}