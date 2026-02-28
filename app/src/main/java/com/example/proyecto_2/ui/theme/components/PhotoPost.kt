package com.example.proyecto_2.ui.theme.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.proyecto_2.models.camera.PhotoWithAddress

@Composable
fun PhotoPost(
    onNavigate: () -> Unit,
    photoWithAddress: PhotoWithAddress,
    modifier: Modifier = Modifier) {

    val photo = photoWithAddress.photo
    val address = photoWithAddress.address

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable{
                onNavigate()
            },
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            // 📷 Imagen
            AsyncImage(
                model = photo.path,
                contentDescription = "Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 📝 Descripción
            if (!photo.description.isNullOrEmpty()) {
                Text(
                    text = photo.description,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

        }
    }
}


@Preview
@Composable
private fun PhotoPost() {
    
}