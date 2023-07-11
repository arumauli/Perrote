package com.example.perritoproyect.ui.screens.components.favorito

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.perritoproyect.model.Dog
import com.example.perritoproyect.navigation.Routes
import com.example.perritoproyect.ui.screens.components.perritolist.PerritoListItem
import com.example.perritoproyect.viewModel.ViewModelDog

@Composable
fun MiPerritoFavorito(navController: NavHostController, viewModelDog: ViewModelDog){

    val favoriteState: MutableState<List<Dog>?> = remember {
        mutableStateOf(arrayListOf())
    }

    viewModelDog.dogsRoom.observeForever {
        favoriteState.value = it?.toList()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (favoriteState.value?.isNotEmpty() == true){
            //Despliega el listado de perros favoritos con su imagen
            LazyColumn(modifier = Modifier
                .fillMaxHeight()){
                items(favoriteState.value?: arrayListOf()){
                    it.favorite = true
                    PerritoListItem(
                        dogItem = it,
                        navController = navController,
                        destination = Routes.Dog.route,
                        viewModelDog = viewModelDog,
                        breeds = it.name,
                    )
                }
            }
        }
    }
}