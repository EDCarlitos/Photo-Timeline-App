package com.example.proyecto_2.ui.theme.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.proyecto_2.models.camera.AddressEntity

@Composable
fun GoToMapsButton(
    address: AddressEntity,
    modifier: Modifier = Modifier) {
    val context = LocalContext.current;

    FloatingActionButton(
        onClick = {

            val lat = address.latitude
            val lng = address.longitude


            val uri = Uri.parse("geo:$lat,$lng?q=$lat,$lng")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            context.startActivity(intent)
        },
        modifier = Modifier
            .padding(24.dp)
    ) {
        Icon(Icons.Default.LocationOn, contentDescription = null)
    }
}