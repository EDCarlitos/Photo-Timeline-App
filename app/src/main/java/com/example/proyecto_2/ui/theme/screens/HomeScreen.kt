package com.example.proyecto_2.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyecto_2.ui.theme.components.PhotoPost

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    Column(modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        PhotoPost()
        PhotoPost()
        PhotoPost()
    }


}


@Preview
@Composable
private fun preview() {
    HomeScreen();
}