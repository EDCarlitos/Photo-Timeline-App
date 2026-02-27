package com.example.proyecto_2.viewModel.Camara

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_2.models.camera.AddressEntity
import com.example.proyecto_2.models.camera.CameraMode
import com.example.proyecto_2.models.camera.PhotoDao
import com.example.proyecto_2.models.camera.PhotoEntity
import com.example.proyecto_2.models.camera.PhotoWithAddress
import com.example.proyecto_2.services.camera.SaveImgageToGallery
import com.example.proyecto_2.services.camera.getAddressFromLatLng
import com.example.proyecto_2.services.camera.getLatLongFromExif
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import java.io.File

class CameraViewModel(
    application: Application,
    val photoDao: PhotoDao
): AndroidViewModel(application) {



    var lensFacing by mutableStateOf(CameraSelector.LENS_FACING_BACK)
        private set
    var cameraMode by  mutableStateOf(CameraMode.PHOTO)
        private set

    private val _photos = MutableStateFlow<List<PhotoWithAddress>>(emptyList())
    val photos: StateFlow<List<PhotoWithAddress>> = _photos


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


    private fun loadPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = photoDao.getPhotosWithAdrress()
            _photos.value = result
        }
    }
    fun onSaveImage(file: File, description: String, hasLocationPermissons: Boolean ){
        viewModelScope.launch {
            val context = getApplication<Application>()

            val saveUri = SaveImgageToGallery(context, file)

            onSaveImageDb(file ,description, saveUri!! ,hasLocationPermissons,context)


        }
    }


    private suspend fun onSaveImageDb(file: File, description: String, uri: Uri, hasLocationPermissons: Boolean, context: Context){
        // 2️⃣ Leer EXIF
        val latLong = getLatLongFromExif(file)

        if (latLong != null) {

            val (lat, lon) = latLong

            // 3️⃣ Obtener dirección
            val addressText =
                getAddressFromLatLng(context, lat, lon)
                    ?: "Dirección desconocida"

            // 4️⃣ Crear AddressEntity
            val address = AddressEntity(
                latitude = lat,
                longitude = lon,
                addressText = addressText,
                timestamp = System.currentTimeMillis(),
                id = null
            )

            // 5️⃣ Crear PhotoEntity
            val photo = PhotoEntity(
                path = uri.toString(),
                description = description,
                timestamp = System.currentTimeMillis(),
                addressId = null
            )

                photoDao.insertAddressWithPhoto(address, photo)

        } else {
            // Si no hay metadata GPS
            val photo = PhotoEntity(
                path = file.absolutePath,
                description = description,
                timestamp = System.currentTimeMillis(),
                addressId = null // o manejar null si lo permites
            )

            photoDao.insertPhoto(photo)
        }
    }

}