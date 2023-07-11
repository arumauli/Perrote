package com.example.perritoproyect.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.perritoproyect.ui.screens.components.favorito.MiPerritoFavorito
import com.example.perritoproyect.ui.screens.components.perrito.DogDetails
import com.example.perritoproyect.ui.screens.main.DogsApp
import com.example.perritoproyect.viewModel.ViewModelDog

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Dog : Routes("dog")
    object Favorite : Routes("favorite")
}

@Composable
fun Navigation(viewModelDog: ViewModelDog) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Home.route) {

        viewModelDog.getBreedsAll()
        viewModelDog.getRoomAllDogs()
        composable(Routes.Home.route) {
            DogsApp(
                navController= navController,
                viewModelDog = viewModelDog)
        }
        composable(Routes.Dog.route.plus("/{breed}")) {
            val breed = it.arguments?.getString("breed").orEmpty()
            DogDetails(
                navController= navController,
                breed = breed,
                viewModelDog = viewModelDog
            )
        }
        composable(Routes.Favorite.route) {
            MiPerritoFavorito(
                navController= navController,
                viewModelDog = viewModelDog
            )
        }
    }
}