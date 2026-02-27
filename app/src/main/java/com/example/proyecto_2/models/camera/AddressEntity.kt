package com.example.proyecto_2.models.camera

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "addresses")
data class AddressEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val latitude: Double,
    val longitude: Double,
    val addressText: String,
    val timestamp: Long

)
