package com.example.proyecto_2.ui.theme.Camera

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_2.viewModel.Camara.CameraViewModel

@Composable
fun CameraPreview(
    context: Context,
    imageCapture: ImageCapture,
    viewModel: CameraViewModel = viewModel(),
    modifier: Modifier = Modifier) {

    val lifecycleOwner = LocalLifecycleOwner.current


    //Camera
    val previewView = remember { PreviewView(context) }
    val camaraProviderFuture = remember {
        ProcessCameraProvider.getInstance(context);
    }


    AndroidView(
        factory = { previewView}
    )

    LaunchedEffect(viewModel.cameraMode, viewModel.lensFacing) {
        camaraProviderFuture.addListener({
            val camaraProvider = camaraProviderFuture.get( )

            val preview: Preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            };

            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(viewModel.lensFacing)
                .build()

            camaraProvider.unbindAll();
            camaraProvider.bindToLifecycle(
                lifecycleOwner = lifecycleOwner,
                cameraSelector = cameraSelector,
                preview,
                imageCapture
            )


        }, ContextCompat.getMainExecutor(context))

    }

}

