package com.example.proyecto_2.viewModel.Camara

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_2.database.DatabaseProvider
import com.example.proyecto_2.models.camera.PhotoWithAddress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapaScreenViewModel(app: Application) : AndroidViewModel(app) {


    private val photoDao = DatabaseProvider.getDatabase(app).photoDao()

    private val _photo = MutableStateFlow<PhotoWithAddress?>(null)
    val photo: StateFlow<PhotoWithAddress?> = _photo

    fun getPhoto(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = photoDao.getPhotoWithAddressById(id)
            _photo.value = result
        }
    }
}

