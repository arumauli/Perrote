package com.example.perritoproyect.ui.screens.main

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.perritoproyect.ui.theme.PerritoGalleryTheme
import com.example.perritoproyect.viewModel.ViewModelDog

@Composable
fun DogsApp(navController: NavHostController, viewModelDog: ViewModelDog) {
    PerritoGalleryTheme() {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colorScheme.background) {
            MainApp(navController,viewModelDog)
        }
    }
}