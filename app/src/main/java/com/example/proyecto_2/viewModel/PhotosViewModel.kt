package com.example.proyecto_2.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_2.database.DatabaseProvider
import com.example.proyecto_2.models.camera.PhotoWithAddress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhotosViewModel(app: Application): AndroidViewModel(app) {

    val photoDao = DatabaseProvider.getDatabase(app).photoDao();

    private val _photos = MutableStateFlow<List<PhotoWithAddress>>(emptyList())
    val photos: StateFlow<List<PhotoWithAddress>> = _photos

    fun loadPhotos() {
        viewModelScope.launch(Dispatchers.IO) {

            val result = photoDao.getPhotosWithAdrress()
            _photos.value = result

        }
    }
}