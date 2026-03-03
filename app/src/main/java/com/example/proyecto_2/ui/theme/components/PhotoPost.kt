package com.example.proyecto_2.ui.theme.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.request.videoFrameMillis
import com.example.proyecto_2.models.camera.PhotoWithAddress
import com.example.proyecto_2.viewModel.navigation.NavigationViewModel
import kotlinx.coroutines.delay
import java.io.File

@Composable
fun PhotoPost(
    onNavigate: ()->Unit,
    photoWithAddress: PhotoWithAddress,
    currentlyPlayingId: Int?,
    onStartPlaying: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val photo = photoWithAddress.photo
    val uri = Uri.parse(photo.path)

    val isVideo = remember(photo.path) {
        context.contentResolver.getType(uri)?.startsWith("video") == true
    }

    val isPlaying = currentlyPlayingId == photo.id

    // 🔥 Player correcto (se recrea solo si cambia el video)
    val player = remember(photo.path) {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(uri))
            prepare()
            playWhenReady = false
        }
    }

    // 🔥 Control reproducción
    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            player.seekTo(0)
            player.play()

            kotlinx.coroutines.delay(5000)

            player.pause()
            onStartPlaying(null)
        } else {
            player.pause()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            player.release()
        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        if (isVideo) {
                            onStartPlaying(photo.id)
                        }
                    },
                    onTap = {onNavigate()}
                )
            },
        shape = RoundedCornerShape(20.dp)
    ) {

        Column {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {

                if (isVideo && isPlaying) {

                    AndroidView(
                        factory = { ctx ->
                            PlayerView(ctx).apply {
                                this.player = player
                                useController = false
                                resizeMode =
                                    AspectRatioFrameLayout.RESIZE_MODE_ZOOM
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

            if (!photo.description.isNullOrBlank()) {
                Text(
                    text = photo.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
@Preview
@Composable
private fun PhotoPost() {
    
}