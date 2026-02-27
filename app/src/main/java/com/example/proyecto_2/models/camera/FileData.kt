package com.example.proyecto_2.models.camera

import android.net.Uri

data class FileData(
    val uri: Uri,
    var lat: Double?,
    var lng: Double?
)
