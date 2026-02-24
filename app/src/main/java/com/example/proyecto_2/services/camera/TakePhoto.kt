package com.example.proyecto_2.services.camera

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import java.io.File


fun TakePhoto(imageCapture: ImageCapture, context: Context){

    var contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "image1")
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MiApp")
    }


    val outPutFileOptions = ImageCapture.OutputFileOptions.Builder(
        context.contentResolver,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        contentValues,
    ).build();


    imageCapture.takePicture(outPutFileOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                print(outputFileResults)
                Log.d("URI", outputFileResults.savedUri.toString())
            }


            override fun onError(exception: ImageCaptureException) {
                print("tilin")
            }
        })
}