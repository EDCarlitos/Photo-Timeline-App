package com.example.proyecto_2.viewModel.Camara

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_2.database.DatabaseProvider
import com.example.proyecto_2.models.camera.AddressEntity
import com.example.proyecto_2.models.camera.FileData
import com.example.proyecto_2.models.camera.PhotoDao
import com.example.proyecto_2.models.camera.PhotoEntity
import com.example.proyecto_2.services.camera.SaveImageToDb
import com.example.proyecto_2.services.camera.SaveImgageToGallery
import com.example.proyecto_2.services.camera.awaitLastLocation
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import java.io.File

class PreviewViewModel(app: Application,
    ): AndroidViewModel(app) {


    private val photoDao = DatabaseProvider.getDatabase(app).photoDao()
    private val saveImage = SaveImageToDb(photoDao)


    var photoFile by mutableStateOf<File?>(null)
    var description by mutableStateOf("")


    fun onSaveImage(hasLocationPermissons: Boolean ){
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
               photoFile!!,
            )
            saveUri.lng = longitude
            saveUri.lat = latitude

             saveImage.onSaveImageDb(saveUri ,description ,hasLocationPermissons,context)
            photoFile = null
            description = ""

        }
    }





    fun cancelPhoto(){
        photoFile = null
        description = ""
    }

}