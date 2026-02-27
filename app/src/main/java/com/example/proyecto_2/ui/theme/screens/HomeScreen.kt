package com.example.proyecto_2.ui.theme.screens

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_2.database.DatabaseProvider
import com.example.proyecto_2.models.camera.PhotoWithAddress
import com.example.proyecto_2.ui.theme.components.PhotoPost
import com.example.proyecto_2.viewModel.Camara.CameraViewModel
import com.example.proyecto_2.viewModel.Camara.CameraViewModelFactory

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val application = context.applicationContext as Application

    // Obtener la base de datos
    val database = DatabaseProvider.getDatabase(context)

    // Crear el factory
    val factory = remember {
        CameraViewModelFactory(
            application,
            database.photoDao()
        )
    }

    // Crear el ViewModel usando el factory
    val viewModel: CameraViewModel = viewModel(factory = factory)


    val photos by viewModel.photos.collectAsState()




    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        items(photos) { item ->
            PhotoPost(photoWithAddress = item)
        }
    }


}


@Preview
@Composable
private fun preview() {
    HomeScreen();
}