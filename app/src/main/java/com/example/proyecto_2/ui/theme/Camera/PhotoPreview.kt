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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
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
import coil.compose.AsyncImage
import java.io.File


@Composable
fun PhotoPreview(
    image: File,
    onSaveImage: () -> Unit,
    modifier: Modifier = Modifier) {

    Box(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            model = image,
            contentDescription = "Taked photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        TextFieldSaveImage({onSaveImage()})




    }
}

@Composable
private fun TextFieldSaveImage(
    onSaveImage: () -> Unit,
    modifier: Modifier = Modifier) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f))
                )
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        var message by remember { mutableStateOf("") }

        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
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