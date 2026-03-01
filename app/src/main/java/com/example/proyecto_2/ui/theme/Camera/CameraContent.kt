package com.example.proyecto_2.ui.theme.Camera

import android.app.Application
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_2.database.Database
import com.example.proyecto_2.database.DatabaseProvider
import com.example.proyecto_2.services.camera.SaveImgageToGallery
import com.example.proyecto_2.services.camera.TakePhoto
import com.example.proyecto_2.viewModel.Camara.CameraViewModel
import com.example.proyecto_2.viewModel.Camara.TakePhotoViewModel
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
        val viewModel: CameraViewModel = viewModel();
        val takePhotoViewModel: TakePhotoViewModel = viewModel();


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
            onTakePhoto = { TakePhoto(
                imageCapture,
                context,
                onTakenPhoto = {
                    takePhotoViewModel.photoFile = it
                    }
                )},
            modifier = Modifier.fillMaxWidth()
                    .align(Alignment.BottomEnd),
            )


    }
}


@Preview
@Composable
private fun prev() {
    CameraContent(true, true)
}