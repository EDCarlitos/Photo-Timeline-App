package com.example.proyecto_2.models.camera

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.util.TableInfo
import java.sql.Timestamp

@Entity("photos",
    foreignKeys = [
        ForeignKey(
            entity = AddressEntity::class,
            parentColumns = ["id"],
            childColumns = ["address_id"],
            onDelete = CASCADE
        )
    ],
    indices = [Index(value = ["addressId"], unique = true)]
    )
data class PhotoEntity (

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo(name = "image_uri")
    val path: String,

    @ColumnInfo(name = "description")
    val timestamp: Timestamp,

    @ColumnInfo(name = "address_id")
    val addressId: Int


    )