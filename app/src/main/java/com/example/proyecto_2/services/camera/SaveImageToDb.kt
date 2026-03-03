package com.example.proyecto_2.services.camera

import android.content.Context
import com.example.proyecto_2.database.DatabaseProvider
import com.example.proyecto_2.models.camera.AddressEntity
import com.example.proyecto_2.models.camera.FileData
import com.example.proyecto_2.models.camera.PhotoDao
import com.example.proyecto_2.models.camera.PhotoEntity

class SaveImageToDb (val photoDao: PhotoDao){


    suspend fun onSaveImageDb(file: FileData, description: String, hasLocationPermissons: Boolean, context: Context){


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

}