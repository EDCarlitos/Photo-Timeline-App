package com.example.proyecto_2.viewModel.Camara

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.proyecto_2.models.camera.CameraMode

class CameraViewModel: ViewModel() {

    var cameraMode by  mutableStateOf(CameraMode.PHOTO)
        private set

    fun  setMode(mode: CameraMode){
        cameraMode = mode
    }

}