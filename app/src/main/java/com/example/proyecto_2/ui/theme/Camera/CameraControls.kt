package com.example.proyecto_2.ui.theme.Camera

import android.content.Context
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.proyecto_2.services.camera.TakePhoto

@Composable
fun CameraBottonControls(
    onTakePhoto: () -> Unit,
    modifier: Modifier = Modifier) {

    Row(modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FlipCameraButton()
        TakePhotoButton(onTakePhoto = { onTakePhoto()})
        FlipCameraButton()

    }

}


@Preview(widthDp = 480)
@Composable
private fun prev() {
    CameraBottonControls({})
}