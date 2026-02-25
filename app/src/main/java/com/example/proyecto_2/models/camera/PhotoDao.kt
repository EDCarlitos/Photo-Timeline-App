package com.example.proyecto_2.models.camera

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface PhotoDao {

    @Transaction
    @Query("SELECT * FROM photos")
    suspend fun getPhotosWithAdrress(): List<PhotoWithAddress>

    @Insert
    suspend fun insertAddress(address: AddressEntity): Int

    @Insert
    suspend fun insertPhoto(photo: PhotoEntity)

    // Esta es la función que tú quieres llamar desde afuera
    @Transaction
    suspend fun insertAddressWithPhoto(address: AddressEntity, photo: PhotoEntity) {
        // 1. Insertamos la dirección y recuperamos el ID generado
        val id = insertAddress(address)

        // 2. Creamos el objeto foto usando ese ID
        val photo = PhotoEntity(
            path = photo.path,
            timestamp = photo.timestamp,
            addressId = id
        )

        // 3. Insertamos la foto
        insertPhoto(photo)
    }


}