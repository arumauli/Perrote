package com.example.perritoproyect.ui.screens.components.perrito

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.perritoproyect.model.Dog
import com.example.perritoproyect.ui.theme.PerritoGalleryTheme
import com.example.perritoproyect.viewModel.ViewModelDog

@Composable
fun DogDetails(navController: NavHostController, breed: String, viewModelDog: ViewModelDog){

    val dogState: MutableState<List<String>?> = remember {
        mutableStateOf(emptyList())
    }
    viewModelDog.dogsDetail.observeForever {
        dogState.value = it
    }

    val dogsRoom: MutableState<List<Dog>?> = remember {
        mutableStateOf(viewModelDog.dogsRoom.value)
    }
    val dogs: MutableState<Dog> = remember {
        mutableStateOf(viewModelDog.dog.value?: Dog())
    }

    viewModelDog.dog.observeForever {
        dogs.value = dogsRoom.value?.find { r -> r.name == breed }?:Dog()
    }


    viewModelDog.dogsRoom.observeForever {
        dogsRoom.value = it
        dogsRoom.value?.find { r -> r.name == breed }?.favorite = true
    }

    PerritoGalleryTheme() {
        Surface(color = MaterialTheme.colorScheme.background) {
            Card(
                modifier= Modifier.fillMaxSize(),
                shape = RectangleShape
            ) {
                Column {

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Gray.copy(0.1f))
                    ) {
                        Column()
                        {
                            if (dogState.value?.isNotEmpty()==true){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = breed.uppercase(),
                                            style = TextStyle(color = Color.Black),
                                            fontWeight = FontWeight.Bold,
                                            fontStyle = FontStyle.Normal,
                                            fontSize = 20.sp)
                                    }

                                    Image(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(40.dp)
                                            .clickable {
                                                if (!dogs.value.favorite){
                                                    viewModelDog.insertRoomDog(Dog(name = breed, image = dogState.value?.firstOrNull()?:""))
                                                }else{
                                                    viewModelDog.deleteRoomDog(dogs.value)
                                                }
                                                dogs.value.favorite = !dogs.value.favorite
                                            },
                                        imageVector = Icons.Default.Favorite,
                                        contentDescription = "Mi Perrito Favorito",
                                        colorFilter = if (dogsRoom.value?.find { r -> r.name == breed }?.favorite == true) ColorFilter.tint(
                                            Color.Red) else ColorFilter.tint(Color.Cyan)
                                    )
                                }
                                Detail(dogState.value)
                            }
                        }
                    }
                }
            }
        }
    }
}

