package com.example.proyecto_2.ui.theme.Camera

import android.content.Context
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_2.services.camera.TakePhoto
import com.example.proyecto_2.viewModel.Camara.CameraViewModel
import com.example.proyecto_2.viewModel.Camara.PreviewViewModel
import java.io.File

@Composable
fun CameraContent(
    hasPermission: Boolean,
    hasLocationPermission: Boolean,
    modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()){

        val context = LocalContext.current


        if(!hasPermission) {
            Text("You cannot use the camera")
            return
        }


        // Crear el ViewModel usando el factory
        val cameraViewModel: CameraViewModel = viewModel();
        val takePhotoViewModel: PreviewViewModel = viewModel();


        //Photo File

        var imageCapture = remember {
            ImageCapture.Builder().build()
        }



        if(takePhotoViewModel.photoFile != null){
            PhotoPreview(hasLocationPermission)
            return
        }



        CameraPreview(context,imageCapture)
        CameraBottonControls(
            onTakePhoto = {
                TakePhoto(
                    imageCapture,
                    context,
                    onTakenPhoto = {
                        takePhotoViewModel.photoFile = it
                    }
                )
            },

            onStartRecording = {

                val videoFile = createTempVideoFile(context)

                cameraViewModel.startRecording(
                    context = context,
                    outputFile = videoFile,
                    onVideoRecorded = { file ->
                        takePhotoViewModel.photoFile = file
                    }
                )
            },

            onStopRecording = {
                cameraViewModel.stopRecording()
            },

            isRecording = cameraViewModel.isRecording,
            modifier = Modifier.align(Alignment.BottomCenter)

        )

    }
}
fun createTempVideoFile(context: Context): File {
    val dir = context.cacheDir
    return File(
        dir,
        "VID_${System.currentTimeMillis()}.mp4"
    )
}

@Preview
@Composable
private fun prev() {
    CameraContent(true, true)
}