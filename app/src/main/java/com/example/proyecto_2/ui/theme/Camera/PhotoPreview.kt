package com.example.proyecto_2.ui.theme.Camera

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import java.io.File


@Composable
fun PhotoPreview(
    image: File,
    modifier: Modifier = Modifier) {

    Box(modifier = modifier.fillMaxSize()) {
        AsyncImage(
            model = image,
            contentDescription = "Taked photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        IconButton(
            onClick = {},
            modifier = Modifier.size(48.dp)
                .align(Alignment.BottomEnd)
                .clip(CircleShape),
        ) {
            Icon(
                imageVector = Icons.Default.AddAPhoto,
                contentDescription =  "Save photo button",
                modifier = Modifier
            )

        }
    }
}

