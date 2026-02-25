package com.example.proyecto_2.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {

    @Volatile
    private var INSTANCE: Database? = null

    fun getDatabase(context: Context): Database{
        return INSTANCE ?: synchronized(this){

            val instance = Room.databaseBuilder(
                context.applicationContext,
                Database::class.java,
                "app_database"
            ).build()

            INSTANCE = instance
            instance

        }
    }

}