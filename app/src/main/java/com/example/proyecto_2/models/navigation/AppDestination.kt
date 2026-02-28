package com.example.proyecto_2.models.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
    val hidden: Boolean  = false
) {
    HOME("Home", Icons.Default.Home),
    CAMERA("Camera", Icons.Default.Camera),
    PROFILE("Profile", Icons.Default.AccountBox),
    MAP(label = "Maps", Icons.Default.Map, true)
}


