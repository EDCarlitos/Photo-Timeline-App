package com.example.proyecto_2.ui.theme.Camera

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CameraBottonControls(modifier: Modifier = Modifier) {

    Row(modifier,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        FlipCameraButton()
        TakePhotoButton()
        FlipCameraButton()

    }

}


@Preview(widthDp = 480)
@Composable
private fun prev() {
    CameraBottonControls()
}