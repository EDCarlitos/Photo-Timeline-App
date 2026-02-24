package com.example.proyecto_2.ui.theme.screens

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.proyecto_2.ui.theme.Camera.CameraContent
import com.example.proyecto_2.ui.theme.Camera.CameraPreview

@Composable
fun CameraScreen(modifier: Modifier = Modifier) {
    var hasPermission by remember { mutableStateOf(false)};
    val context = LocalContext.current

    val camaraPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
            if(it){
                hasPermission = it
            }else{
                Toast.makeText(context, "No tenemos permiso para usar la camara",
                    Toast.LENGTH_SHORT).show();
            }

        }

    LaunchedEffect(Unit) {
        val permisionCheck = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        )
        if(permisionCheck != PackageManager.PERMISSION_GRANTED){
            camaraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }else{
            hasPermission = true
        }
    }


    CameraContent(hasPermission)



}




@Preview
@Composable
private fun prev() {
    CameraScreen()
}