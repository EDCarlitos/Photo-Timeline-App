package com.example.proyecto_2.viewModel.Camara

import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.location.Location
import android.net.Uri
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.video.FileOutputOptions
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.camera.video.VideoRecordEvent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_2.database.DatabaseProvider
import com.example.proyecto_2.models.camera.AddressEntity
import com.example.proyecto_2.models.camera.CameraMode
import com.example.proyecto_2.models.camera.FileData
import com.example.proyecto_2.models.camera.PhotoDao
import com.example.proyecto_2.models.camera.PhotoEntity
import com.example.proyecto_2.services.camera.SaveImgageToGallery
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import kotlin.coroutines.resume

class CameraViewModel(): ViewModel(){



    var lensFacing by mutableStateOf(CameraSelector.LENS_FACING_BACK)
        private set
    var cameraMode by  mutableStateOf(CameraMode.PHOTO)
        private set
    
    var recordingTime by mutableStateOf(0L)
        private set
    
    
    var isRecording by mutableStateOf(false)
        private set

    var videoCapture by mutableStateOf<VideoCapture<Recorder>?>(null)
    var recording by mutableStateOf<Recording?>(null)
        private set
    private var timerJob: Job? = null
    fun  setMode(mode: CameraMode){
        cameraMode = mode
    }

    fun toggleCamera() {
        lensFacing =
            if (lensFacing == CameraSelector.LENS_FACING_BACK)
                CameraSelector.LENS_FACING_FRONT
            else
                CameraSelector.LENS_FACING_BACK
    }



    fun startRecording(
        context: Context,
        outputFile: File,
        onVideoRecorded: (File) -> Unit
    ) {

        val videoCapture = videoCapture ?: return
        val fileOutputOptions = FileOutputOptions
            .Builder(outputFile)
            .build()

        recording = videoCapture.output
            .prepareRecording(context, fileOutputOptions)
            .start(ContextCompat.getMainExecutor(context)) { event ->

                when (event) {

                    is VideoRecordEvent.Start -> {
                        isRecording = true
                        startTimer()   // 👈 AQUÍ
                    }

                    is VideoRecordEvent.Finalize -> {
                        isRecording = false
                        recording = null
                        stopTimer()

                        if (!event.hasError()) {
                            onVideoRecorded(outputFile)
                        }
                    }
                }
            }
    }



    private fun startTimer() {
        recordingTime = 0L
        timerJob = viewModelScope.launch {
            while (isRecording) {
                delay(1000)
                recordingTime++
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
        recordingTime = 0L
    }

    fun stopRecording() {
        recording?.stop()
        isRecording = false
    }



    fun isVideo(): Boolean {
        return this.cameraMode == CameraMode.VIDEO
    }





}