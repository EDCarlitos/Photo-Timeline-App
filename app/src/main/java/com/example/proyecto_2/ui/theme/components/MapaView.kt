package com.example.proyecto_2.ui.theme.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.proyecto_2.models.camera.AddressEntity
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapaView(
    address: AddressEntity,
    modifier: Modifier = Modifier) {

    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { context ->
            MapView(context).apply {
                setMultiTouchControls(true)
            }
        },
        update = { mapView ->

            val punto = GeoPoint(
                address.latitude,
                address.longitude
            )

            mapView.controller.setZoom(16.0)
            mapView.controller.setCenter(punto)

            mapView.overlays.clear() // 🔥 IMPORTANTE limpiar overlays

            val marker = Marker(mapView).apply {
                position = punto
                setAnchor(
                    Marker.ANCHOR_CENTER,
                    Marker.ANCHOR_BOTTOM
                )
                title = "Ubicación"
            }

            mapView.overlays.add(marker)
            mapView.invalidate()
        }
    )
}