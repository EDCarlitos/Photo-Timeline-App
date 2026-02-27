package com.example.proyecto_2

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyecto_2.models.navigation.AppDestinations
import com.example.proyecto_2.ui.theme.Proyecto_2Theme
import com.example.proyecto_2.ui.theme.screens.CameraScreen
import com.example.proyecto_2.ui.theme.screens.GalleryScreen
import com.example.proyecto_2.ui.theme.screens.HomeScreen
import com.example.proyecto_2.ui.theme.screens.MapaScreen
import com.example.proyecto_2.viewModel.Camara.NavigationViewModel
import org.osmdroid.config.Configuration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(
            applicationContext,
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
        )

        Configuration.getInstance().userAgentValue = packageName

        enableEdgeToEdge()
        setContent {
            Proyecto_2Theme {
                Proyecto_2App()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun Proyecto_2App(navigationViewModel: NavigationViewModel = viewModel()) {



    var idPhoto by remember { mutableStateOf(0) }


    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                if(it.hidden) return@forEach
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == navigationViewModel.currentDestination,
                    onClick = {  navigationViewModel.navigate(it)}
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            when (navigationViewModel.currentDestination) {
                AppDestinations.HOME -> {
                    HomeScreen(
                        onNavigateToMap = { id ->
                            idPhoto = id
                             navigationViewModel.navigate(AppDestinations.MAP)
                        },
                    Modifier.padding(innerPadding))
                }
                AppDestinations.FAVORITES -> {
                    CameraScreen(Modifier.padding(innerPadding))
                }
                AppDestinations.PROFILE -> {
                    GalleryScreen(Modifier.padding(innerPadding))
                }

                AppDestinations.MAP ->{
                        MapaScreen(
                            idPhoto = idPhoto,
                            Modifier.padding(innerPadding)
                        )
                }
            }
        }

    }
}

