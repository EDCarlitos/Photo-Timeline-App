package com.example.proyecto_2.models.camera

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity("photos",
    foreignKeys = [
        ForeignKey(
            entity = AddressEntity::class,
            parentColumns = ["id"],
            childColumns = ["address_id"],
            onDelete = CASCADE
        )
    ],
    indices = [Index(value = ["address_id"], unique = true)]
    )
data class PhotoEntity (

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "image_uri")
    val path: String,

    @ColumnInfo(name = "description")
    val description: String?,

    val timestamp: Long,

    @ColumnInfo(name = "address_id")
    val addressId: Long?


    )