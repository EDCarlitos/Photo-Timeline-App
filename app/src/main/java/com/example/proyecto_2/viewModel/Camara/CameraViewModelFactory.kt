package com.example.proyecto_2.viewModel.Camara

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.proyecto_2.models.camera.PhotoDao

class CameraViewModelFactory(
    private val application: Application,
    private val photoDao: PhotoDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CameraViewModel(application, photoDao) as T
    }
}