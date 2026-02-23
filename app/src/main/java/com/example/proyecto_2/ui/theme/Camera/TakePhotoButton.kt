package com.example.proyecto_2.ui.theme.Camera

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_2.viewModel.Camara.CameraViewModel

@Composable
fun TakePhotoButton(
    onTakePhoto: () -> Unit,
    viewModel: CameraViewModel = viewModel(),
    modifier: Modifier = Modifier) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(80.dp)
            .clip(CircleShape)
            .border(2.dp,Color.White, CircleShape)
            .background(Color.Transparent)
            .clickable { onTakePhoto() }
    ) {

        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
    }



}

@Preview
@Composable
private fun prev() {
    TakePhotoButton({})
}