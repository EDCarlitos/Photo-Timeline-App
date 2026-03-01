package com.example.proyecto_2.viewModel.Camara

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_2.database.DatabaseProvider
import com.example.proyecto_2.models.camera.AddressEntity
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

class TakePhotoViewModel(app: Application): AndroidViewModel(app) {


    val photoDao: PhotoDao = DatabaseProvider.getDatabase(app).photoDao();
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

            onSaveImageDb(saveUri ,description ,hasLocationPermissons,context)
            photoFile = null
            description = ""

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


        if (file.lat != null && file.lng != null) {


            val address = AddressEntity(
                latitude = file.lat!!,
                longitude = file.lng!!,
                addressText = "",
                timestamp = System.currentTimeMillis(),
                id = null
            )

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
                addressId = null
            )

            photoDao.insertPhoto(photo)
        }
    }



    fun cancelPhoto(){
        photoFile = null
        description = ""
    }

}