package com.example.proyecto_2.ui.theme.Camera

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FlipCameraIos
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_2.R
import com.example.proyecto_2.viewModel.Camara.CameraViewModel

@Composable
fun FlipCameraButton(
    viewModel: CameraViewModel = viewModel(),
    modifier: Modifier = Modifier) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable {
                // voltear cámara
                viewModel.toggleCamera()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            imageVector = Icons.Default.FlipCameraIos,
            contentDescription = stringResource( R.string.flip_camera),
            tint = Color.White
        )
    }
}


@Preview
@Composable
private fun pre() {
    FlipCameraButton()
}