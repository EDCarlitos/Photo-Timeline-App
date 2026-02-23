package com.example.proyecto_2.ui.theme.Camera

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CameraContent(
    hasPermission: Boolean,
    modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()){

        if(!hasPermission) {
            Text("You cannot use the camera")
            return
        }

        CameraPreview()
        CameraBottonControls(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomEnd),

        )
    }
}


@Preview
@Composable
private fun prev() {
    CameraContent(true)
}