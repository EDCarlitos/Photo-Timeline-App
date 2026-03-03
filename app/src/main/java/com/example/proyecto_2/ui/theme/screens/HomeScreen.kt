package com.example.proyecto_2.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_2.ui.theme.components.PhotoPost
import com.example.proyecto_2.viewModel.PhotosViewModel

@Composable
fun HomeScreen(
    onNavigateToMap: (Int)-> Unit,
    modifier: Modifier = Modifier) {


    val viewModel: PhotosViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.loadPhotos()
    }

    val photos by viewModel.photos.collectAsState()
    var currentlyPlayingId by mutableStateOf<Int?>(null)



    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {


        items(photos) { item ->
            PhotoPost(
                onNavigate = { onNavigateToMap(item.photo.id!!)},
                photoWithAddress = item,
                currentlyPlayingId = currentlyPlayingId,
                onStartPlaying = { id ->
                    currentlyPlayingId = id
                }
            )

        }
    }


}
