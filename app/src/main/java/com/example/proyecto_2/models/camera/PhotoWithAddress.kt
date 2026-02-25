package com.example.proyecto_2.models.camera

import androidx.room.Embedded
import androidx.room.Relation

data class PhotoWithAddress (

    @Embedded val photo: PhotoEntity,

    @Relation(
        parentColumn = "address_id",
        entityColumn = "id"
    )
    val address: AddressEntity

    )