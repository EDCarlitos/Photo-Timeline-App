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
import androidx.lifecycle.viewModelScope
import com.example.proyecto_2.models.camera.AddressEntity
import com.example.proyecto_2.models.camera.CameraMode
import com.example.proyecto_2.models.camera.FileData
import com.example.proyecto_2.models.camera.PhotoDao
import com.example.proyecto_2.models.camera.PhotoEntity
import com.example.proyecto_2.models.camera.PhotoWithAddress
import com.example.proyecto_2.services.camera.SaveImgageToGallery
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import kotlin.coroutines.resume

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


    fun loadPhotos() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = photoDao.getPhotosWithAdrress()
            _photos.value = result
        }
    }
    fun onSaveImage(file: File, description: String, hasLocationPermissons: Boolean ){
        viewModelScope.launch {
            val context = getApplication<Application>()

            val fusedLocationClient =
                LocationServices.getFusedLocationProviderClient(context)

            var latitude: Double? = null
            var longitude: Double? = null

            if (hasLocationPermissons) {
                val location = fusedLocationClient.awaitLastLocation()
                latitude = location?.latitude
                longitude = location?.longitude
            }

            val saveUri = SaveImgageToGallery(
                context,
                file,
            )
            saveUri.lng = longitude
            saveUri.lat = latitude

            onSaveImageDb(saveUri ,description ,hasLocationPermissons,context)


        }
    }


    @SuppressLint("MissingPermission")
    suspend fun FusedLocationProviderClient.awaitLastLocation(): Location? =
        suspendCancellableCoroutine { cont ->
            lastLocation
                .addOnSuccessListener { location ->
                    cont.resume(location)
                }
                .addOnFailureListener {
                    cont.resume(null)
                }
        }
    private suspend fun onSaveImageDb(file: FileData, description: String, hasLocationPermissons: Boolean, context: Context){
        // 2️⃣ Leer EXIF


        if (file.lat != null && file.lng != null) {

          
            // 4️⃣ Crear AddressEntity
            val address = AddressEntity(
                latitude = file.lat!!,
                longitude = file.lng!!,
                addressText = "",
                timestamp = System.currentTimeMillis(),
                id = null
            )

            // 5️⃣ Crear PhotoEntity
            val photo = PhotoEntity(
                path = file.uri.toString(),
                description = description,
                timestamp = System.currentTimeMillis(),
                addressId = null
            )

            photoDao.insertAddressWithPhoto(address, photo)

        } else {
            // Si no hay metadata GPS
            val photo = PhotoEntity(
                path = file.uri.toString(),
                description = description,
                timestamp = System.currentTimeMillis(),
                addressId = null // o manejar null si lo permites
            )

            photoDao.insertPhoto(photo)
        }
    }

}