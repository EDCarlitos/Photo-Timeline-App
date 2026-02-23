package com.example.proyecto_2.services.camera

import android.content.Context
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import java.io.File


fun TakePhoto(imageCapture: ImageCapture, context: Context){
    val file = File.createTempFile("image", ".jpg",)

    val outPutFileOptions = ImageCapture.OutputFileOptions.Builder(file).build();
    imageCapture.takePicture(outPutFileOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                print(outputFileResults)

            }


            override fun onError(exception: ImageCaptureException) {
                print("tilin")
            }
        })
}