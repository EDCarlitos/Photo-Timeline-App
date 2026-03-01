package com.example.proyecto_2.viewModel.Camara

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Location
import androidx.camera.core.CameraSelector
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_2.database.DatabaseProvider
import com.example.proyecto_2.models.camera.AddressEntity
import com.example.proyecto_2.models.camera.CameraMode
import com.example.proyecto_2.models.camera.FileData
import com.example.proyecto_2.models.camera.PhotoDao
import com.example.proyecto_2.models.camera.PhotoEntity
import com.example.proyecto_2.services.camera.SaveImgageToGallery
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import kotlin.coroutines.resume

class CameraViewModel(): ViewModel(){



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