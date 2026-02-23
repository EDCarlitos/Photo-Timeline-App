package com.example.proyecto_2.viewModel.Camara

import androidx.camera.core.CameraSelector
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.proyecto_2.models.camera.CameraMode

class CameraViewModel: ViewModel() {

    var lensFacing by mutableStateOf(CameraSelector.LENS_FACING_BACK)
        private set
    var cameraMode by  mutableStateOf(CameraMode.PHOTO)
        private set

    fun  setMode(mode: CameraMode){
        cameraMode = mode
    }

    fun toggleCamera() {
        lensFacing =
            if (lensFacing == CameraSelector.LENS_FACING_BACK)
                CameraSelector.LENS_FACING_FRONT
            else
                CameraSelector.LENS_FACING_BACK
    }

}