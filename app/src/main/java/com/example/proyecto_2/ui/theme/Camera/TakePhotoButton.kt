package com.example.proyecto_2.ui.theme.Camera

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_2.models.camera.CameraMode
import com.example.proyecto_2.viewModel.Camara.CameraViewModel

@Composable
fun TakePhotoButton(
    onTakePhoto: () -> Unit,
    viewModel: CameraViewModel = viewModel(),
    modifier: Modifier = Modifier) {

    val outerSize = 84.dp
    val innerSize by animateDpAsState(
        targetValue = when {
            viewModel.cameraMode == CameraMode.VIDEO && viewModel.isRecording -> 36.dp
            else -> 64.dp
        },
        label = "innerSizeAnim"
    )

    val innerShape = when {
        viewModel.cameraMode == CameraMode.VIDEO && viewModel.isRecording-> RoundedCornerShape(12.dp)
        else -> CircleShape
    }

    val innerColor = when (viewModel.cameraMode) {
        CameraMode.PHOTO -> Color.White
        CameraMode.VIDEO -> Color.Red
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(outerSize)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.15f))
            .border(3.dp, Color.White, CircleShape)
            .clickable { onTakePhoto() }
    ) {

        // Anillo interior estilo cámara real
        if (viewModel.cameraMode == CameraMode.PHOTO) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White.copy(alpha = 0.7f), CircleShape)
            )
        }

        // Botón central
        Box(
            modifier = Modifier
                .size(innerSize)
                .clip(innerShape)
                .background(innerColor)
        )
    }

}

@Preview
@Composable
private fun prev() {
    TakePhotoButton({})
}