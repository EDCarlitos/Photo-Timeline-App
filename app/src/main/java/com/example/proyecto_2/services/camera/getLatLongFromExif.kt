package com.example.proyecto_2.services.camera

import android.media.ExifInterface
import java.io.File


fun getLatLongFromExif(file: File): Pair<Double, Double>? {
    val exif = ExifInterface(file)

    val latLong = FloatArray(2)
    return if (exif.getLatLong(latLong)) {
        Pair(latLong[0].toDouble(), latLong[1].toDouble())
    } else {
        null
    }
}