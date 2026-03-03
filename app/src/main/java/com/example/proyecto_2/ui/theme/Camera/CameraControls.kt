package com.example.proyecto_2.ui.theme.Camera

import android.content.Context
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_2.models.camera.CameraMode
import com.example.proyecto_2.services.camera.TakePhoto
import com.example.proyecto_2.ui.theme.components.ModeButton
import com.example.proyecto_2.viewModel.Camara.CameraViewModel

@Composable
fun CameraBottonControls(
    onTakePhoto: () -> Unit,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    isRecording: Boolean,
    modifier: Modifier = Modifier) {

        val viewModel: CameraViewModel = viewModel()

        Column (
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),

        ) {


            if (isRecording) {
                RecordingTimer(time = viewModel.recordingTime)
            }


            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                ModeButton(
                    text = "Cámara",
                    isSelected = viewModel.cameraMode == CameraMode.PHOTO,
                    onClick = {viewModel.setMode(CameraMode.PHOTO)}

                )

                ModeButton(
                    text = "Video",
                    isSelected = viewModel.cameraMode == CameraMode.VIDEO,
                    onClick = {viewModel.setMode(CameraMode.VIDEO)}
                )

            }


            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly){

                FlipCameraButton()
                TakePhotoButton(onTakePhoto = {
                    if(viewModel.isVideo()){
                        if (isRecording) {
                            onStopRecording()
                        } else {
                            onStartRecording()
                        }
                    }else{
                        onTakePhoto()
                    }




                })

            }


        }



}


@Preview(widthDp = 480)
@Composable
private fun prev() {
    CameraBottonControls(
        {},
        {},
        {},
        true,
        modifier = Modifier.fillMaxWidth())
}