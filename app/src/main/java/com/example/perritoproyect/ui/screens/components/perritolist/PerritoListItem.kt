package com.example.perritoproyect.ui.screens.components.perritolist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.perritoproyect.R
import com.example.perritoproyect.model.Dog
import com.example.perritoproyect.viewModel.ViewModelDog

@Composable
fun PerritoListItem(
    dogItem: Dog,
    navController: NavController,
    destination: String,
    viewModelDog: ViewModelDog,
    breeds:String
) {

    val dogsRoom: MutableState<List<Dog>?> = remember {
        mutableStateOf(viewModelDog.dogsRoom.value)
    }

    viewModelDog.dogsRoom.observeForever {
        dogsRoom.value = it
        dogsRoom.value?.find { r -> r.name == dogItem.name }?.favorite = true
    }

    Card(
        shape = RectangleShape
    ) {
        Column {

            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .background(Color.Gray.copy(0.1f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize())
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = dogItem.name.uppercase(),
                                style = TextStyle(color = Color.Black),
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Normal,
                                fontSize = 30.sp)
                        }
                        Image(
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(40.dp)
                                .clickable
                                {
                                    dogItem.favorite = !dogItem.favorite
                                    if (dogItem.favorite) {
                                        viewModelDog.insertRoomDog(dogItem)
                                    } else {
                                        viewModelDog.deleteRoomDog(dogItem)
                                    }

                                },

                            imageVector = ImageVector.vectorResource(id = R.drawable.miperrofavorito),
                            contentDescription = "Mi Perrito Favorito",
                            colorFilter = if (dogsRoom.value?.find { r -> r.name == dogItem.name }?.favorite == true) ColorFilter.tint(
                                Color.Red) else ColorFilter.tint(Color.Black)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(destination.plus("/${breeds}"))
                                viewModelDog.getDogBreedImages(breeds)
                            },
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        AsyncImage(
                            model = dogItem.image,
                            contentDescription = dogItem.image,
                            modifier = Modifier.fillMaxHeight(),
                            contentScale = ContentScale.Fit,


                            )
                    }
                }
            }
        }
    }
}
