package com.example.proyecto_2.viewModel.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.proyecto_2.models.navigation.AppDestinations

class NavigationViewModel : ViewModel(){



    var currentDestination by mutableStateOf(AppDestinations.HOME)
        private set



    fun navigate(destination: AppDestinations){
        currentDestination = destination
    }




}