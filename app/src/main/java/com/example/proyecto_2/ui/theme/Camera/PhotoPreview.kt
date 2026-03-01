package com.example.proyecto_2.ui.theme.Camera

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.proyecto_2.viewModel.Camara.TakePhotoViewModel
import java.io.File


@Composable
fun PhotoPreview(
    hasLocationPermissions: Boolean,
    takePhotoViewModel: TakePhotoViewModel = viewModel(),
    modifier: Modifier = Modifier) {


    Box(modifier = modifier.fillMaxSize()) {

        AsyncImage(
            model = takePhotoViewModel.photoFile,
            contentDescription = "Taked photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .statusBarsPadding()
                .padding(16.dp)
                .size(40.dp)
                .clip(CircleShape)
                .background(
                    Color.Black.copy(0.6F)
                ),
            onClick = { takePhotoViewModel.cancelPhoto()},
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Cancel Photo"
            )
        }


        TextFieldSaveImage(
            {
                takePhotoViewModel.onSaveImage(hasLocationPermissions)
            },
            {message -> takePhotoViewModel.description = message},
            modifier = Modifier
                .align(Alignment.BottomEnd),
            message = takePhotoViewModel.description
        )



    }
}

@Composable
private fun TextFieldSaveImage(
    onSaveImage: () -> Unit,
    onChangeMessage: (message: String) -> Unit,
    message: String,
    modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                )
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {


        OutlinedTextField(
            value = message,
            onValueChange = onChangeMessage,
            placeholder = { Text("Agregar un mensaje...") },
            modifier = Modifier
                .weight(1f)
                .heightIn(35.dp,150.dp),
            shape = RoundedCornerShape(25.dp),
            colors = OutlinedTextFieldDefaults
                .colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White
                )
        )

        Spacer(modifier = Modifier.width(12.dp))

        IconButton(
            modifier = Modifier
                .size(50.dp)
                .background(
                    color = Color.Gray,
                    shape = CircleShape
                ),
            onClick = {onSaveImage()},
        ) {
            Icon(
                imageVector = Icons.Default.AddAPhoto,
                contentDescription = "Enviar",
                tint = Color.White
            )
        }
    }
}