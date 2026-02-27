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


    @Query(value = "SELECT * FROM photos WHERE id = :id ORDER BY id DESC" )
    suspend fun getPhotoWithAddressById(id: Int): PhotoWithAddress

    @Insert
    suspend fun insertAddress(address: AddressEntity): Long

    @Insert
    suspend fun insertPhoto(photo: PhotoEntity)

    // Esta es la función que tú quieres llamar desde afuera
    @Transaction
    suspend fun insertAddressWithPhoto(address: AddressEntity, photo: PhotoEntity) {
        // 1. Insertamos la dirección y recuperamos el ID generado
        val id = insertAddress(address)

        // 2. Creamos la foto con el ID de la dirección
        val photoEntity = PhotoEntity(
            path = photo.path,
            timestamp = photo.timestamp,
            addressId = id,
            id = null,
            description = photo.description
        )

        // 3. Insertamos la foto correcta
        insertPhoto(photoEntity)
    }


}