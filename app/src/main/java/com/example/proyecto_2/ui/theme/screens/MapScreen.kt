@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.proyecto_2.ui.theme.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.proyecto_2.models.navigation.AppDestinations
import com.example.proyecto_2.ui.theme.components.GoToMapsButton
import com.example.proyecto_2.ui.theme.components.MapaView
import com.example.proyecto_2.viewModel.Camara.MapaScreenViewModel
import com.example.proyecto_2.viewModel.navigation.NavigationViewModel

@Composable
fun MapaScreen(idPhoto: Int, modifier: Modifier = Modifier) {

    val viewmodel: MapaScreenViewModel = viewModel()
    val photo by viewmodel.photo.collectAsState()

    LaunchedEffect(idPhoto) {
        viewmodel.getPhoto(idPhoto)
    }

    if(photo == null)return





    Box(modifier = modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            TopBar()


            // 🖼 IMAGEN PRINCIPAL
            AsyncImage(
                model = photo!!.photo.path,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )

            // 📄 INFORMACIÓN
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {


                Text(
                    text = photo!!.photo.description!!,
                    style = MaterialTheme.typography.titleMedium
                )

            }

            Spacer(modifier = Modifier.height(8.dp))

            // 🗺 MAPA EN CARD
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {

                if (photo!!.address != null) {

                    MapaView(photo!!.address!!)

                } else {

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No hay ubicación disponible")
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        // 📍 BOTÓN FLOTANTE
        if (photo!!.address != null) {
            GoToMapsButton(photo!!.address!!,
                modifier = Modifier
                .align(Alignment.BottomEnd)
            )
        }



    }

}


@Composable
fun TopBar(
    viewModel: NavigationViewModel = viewModel(),
    modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text("Photo")
        },
        navigationIcon = {
            IconButton(
                onClick = {viewModel.navigate(AppDestinations.HOME)}
            ){
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back home",
                )
            }
        },
    )
}