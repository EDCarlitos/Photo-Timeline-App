package com.example.proyecto_2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyecto_2.models.camera.AddressEntity
import com.example.proyecto_2.models.camera.PhotoDao
import com.example.proyecto_2.models.camera.PhotoEntity

@Database(
    entities = [PhotoEntity::class, AddressEntity::class],
    version = 1
)
abstract class Database: RoomDatabase() {
    abstract fun photoDao(): PhotoDao

}