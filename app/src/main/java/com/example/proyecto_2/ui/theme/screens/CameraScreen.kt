package com.example.proyecto_2.ui.theme.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat

@Composable
fun CameraScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    var camaraPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            if(it){
            }else{

            }

        }

    LaunchedEffect(Unit) {
        val permmisionCheck = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        )
        if(permmisionCheck != PackageManager.PERMISSION_GRANTED){
            camaraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Text("CAMARA SCREEN")


}

@Preview
@Composable
private fun prev() {
    CameraScreen()
}