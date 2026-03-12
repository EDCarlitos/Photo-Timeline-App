@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.proyecto_2.ui.theme.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import com.example.proyecto_2.models.navigation.AppDestinations
import com.example.proyecto_2.ui.theme.components.GoToMapsButton
import com.example.proyecto_2.ui.theme.components.MapaView
import com.example.proyecto_2.viewModel.Camara.MapaScreenViewModel
import com.example.proyecto_2.viewModel.navigation.NavigationViewModel

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun MapaScreen(
    idPhoto: Int,
    modifier: Modifier = Modifier
) {

    val viewmodel: MapaScreenViewModel = viewModel()
    val photoWithAddress by viewmodel.photo.collectAsState()

    LaunchedEffect(idPhoto) {
        viewmodel.getPhoto(idPhoto)
    }

    if (photoWithAddress == null) return

    val photo = photoWithAddress!!.photo
    val address = photoWithAddress!!.address

    val context = LocalContext.current
    val uri = remember(photo.path) { Uri.parse(photo.path) }

    val isVideo = remember(photo.path) {
        context.contentResolver.getType(uri)?.startsWith("video") == true
    }

    Scaffold(
        topBar = {
            TopBar()
        },
        contentColor = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier.background(MaterialTheme.colorScheme.tertiary)
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiary)
        ) {

            Column(
                verticalArrangement = Arrangement.Top,
                modifier = modifier.fillMaxSize()
            ) {

                // 🎬 VIDEO / IMAGEN (60%)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4f)
                ) {
                    if (isVideo) {

                        val player = remember(photo.path) {
                            ExoPlayer.Builder(context).build().apply {
                                setMediaItem(MediaItem.fromUri(uri))
                                prepare()
                                playWhenReady = true
                            }
                        }

                        DisposableEffect(Unit) {
                            onDispose { player.release() }
                        }

                        AndroidView(
                            factory = { ctx ->
                                PlayerView(ctx).apply {
                                    this.player = player
                                    useController = true
                                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )

                    } else {

                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                // 🗺 MAPA (40%)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f)
                        .padding(16.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        if (address != null) {
                            MapaView(address)
                        }
                    }
                }
            }

            // BOTÓN FLOTANTE
            if (photoWithAddress!!.address != null) {
                GoToMapsButton(
                    photoWithAddress!!.address!!,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun TopBar(
    viewModel: NavigationViewModel = viewModel(),
    modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text("Photo")
        },
        navigationIcon = {
            IconButton(
                onClick = {viewModel.navigate(AppDestinations.HOME)}
            ){
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back home",
                )
            }
        },
    )
}