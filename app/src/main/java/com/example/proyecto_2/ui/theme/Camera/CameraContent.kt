package com.example.proyecto_2.ui.theme.Camera

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
import com.example.proyecto_2.services.camera.SaveImgageToGallery
import com.example.proyecto_2.services.camera.TakePhoto
import java.io.File

@Composable
fun CameraContent(
    hasPermission: Boolean,
    modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()){

        if(!hasPermission) {
            Text("You cannot use the camera")
            return
        }


        val context = LocalContext.current

        //Photo File
        var photoFile by
            remember { mutableStateOf<File?>(null) }

        var imageCapture = remember {
            ImageCapture.Builder().build()
        }

        if(photoFile != null){
            PhotoPreview(photoFile as File, {
                SaveImgageToGallery(context, photoFile!!)

            })
        }else{

            CameraPreview(context,imageCapture)
            CameraBottonControls(
                onTakePhoto = { TakePhoto(imageCapture,context,
                    onTakenPhoto = {
                    photoFile=it
                }
                )},
                modifier = Modifier.fillMaxWidth()
                    .align(Alignment.BottomEnd),


                )

        }


    }
}


@Preview
@Composable
private fun prev() {
    CameraContent(true)
}