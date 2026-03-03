package com.example.proyecto_2.services.camera

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever

fun getVideoFrame(path: String): Bitmap? {
    return try {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(path)
        val bitmap = retriever.getFrameAtTime(
            1_000_000, // 1 segundo
            MediaMetadataRetriever.OPTION_CLOSEST_SYNC
        )
        retriever.release()
        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}