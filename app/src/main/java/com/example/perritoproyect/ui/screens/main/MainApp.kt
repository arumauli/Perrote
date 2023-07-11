package com.example.perritoproyect.ui.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.perritoproyect.R
import com.example.perritoproyect.model.Dog
import com.example.perritoproyect.navigation.Routes
import com.example.perritoproyect.ui.screens.components.perritolist.PerritoListItem
import com.example.perritoproyect.viewModel.ViewModelDog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(navController: NavHostController, viewModelDog: ViewModelDog) {

    //Estado del dialogo, se muestra o no se muestra, se inicializa en falso
    val dialogState: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    //Estado de la lista de perros principales
    val dogsState: MutableState<List<String>?> = remember {
        mutableStateOf(viewModelDog.dogs.value)
    }
    //Observador cuando la lista de perros cambia
    viewModelDog.dogs.observeForever {
        dogsState.value = it
    }

    //Estado de la lista de perros de home
    val dogsHome: MutableState<List<Dog>?> = remember {
        mutableStateOf(viewModelDog.dogsHome.value)
    }
    //Observador cuando la lista de perros de home cambia
    viewModelDog.dogsHome.observeForever {
        dogsHome.value = it
    }


    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(stringResource(id = R.string.app_name)) },
            actions = {
                //Muestra el icono de lupa cuando la lista de perros principales no esta vacía
                if (dogsState.value?.isNotEmpty() == true){
                    AppBarAction(
                        imageVector = Icons.Default.Search,
                        onClick = {
                            //Cambia el estado del dialogo
                            dialogState.value = true
                        }
                    )
                }
                AppBarAction(
                    imageVector = ImageVector.vectorResource(id = R.drawable.miperrofavorito),
                    onClick = {
                        navController.navigate(Routes.Favorite.route)
                    }
                )
            }
        )

        if (dogsHome.value?.isNotEmpty() == true){
            //Despliega el listado de perros con su imagen
            LazyColumn(modifier = Modifier
                .fillMaxHeight()){
                items(dogsHome.value?.toList()?: arrayListOf()){

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

        if (dialogState.value){
            DialogDogs(
                dialogState = dialogState,
                dogsState = dogsState,
                navController = navController,
                destination = Routes.Dog.route,
                viewModelDog = viewModelDog)
        }
    }
}



@Composable
fun DialogDogs(
    dialogState: MutableState<Boolean>,
    dogsState: MutableState<List<String>?>,
    navController: NavHostController,
    destination: String,
    viewModelDog: ViewModelDog
){
    Dialog(
        onDismissRequest = { dialogState.value = false },
        content = {
            CompleteDialogContent(
                title = "Perrito List",
                dialogState = dialogState,
                successButtonText = "OK"
            ) {
                BodyContent(dogsState,navController,destination, viewModelDog)
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    )
}

@Composable
fun BodyContent(
    dogs: MutableState<List<String>?>,
    navController: NavHostController,
    destination: String,
    viewModelDog: ViewModelDog
) {
    LazyColumn(modifier = Modifier
        .fillMaxHeight()){
        items(dogs.value?.toList()?: arrayListOf()){
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .clickable {
                    navController.navigate(destination.plus("/${it}"))
                    viewModelDog.getDogBreedImages(it)
                }
            ) {
                Column() {
                    Row() {
                        Text(
                            text = it,
                            fontSize = 20.sp,
                            fontStyle = FontStyle.Normal,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.Bold
                        )
                        if (viewModelDog.dogsRoom.value?.find { v-> v.name == it }!= null){
                            Image(
                                modifier = Modifier
                                    .size(15.dp),
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Mi Perrito Favorito",
                                colorFilter = ColorFilter.tint(Color.Red)
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun CompleteDialogContent(
    title: String,
    dialogState: MutableState<Boolean>,
    successButtonText: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth(1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TitleAndButton(title, dialogState)
            AddBody(content)
            BottomButtons(successButtonText, dialogState = dialogState)
        }
    }
}

@Composable
private fun TitleAndButton(title: String, dialogState: MutableState<Boolean>) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, fontSize = 24.sp)
            IconButton(modifier = Modifier.then(Modifier.size(24.dp)),
                onClick = {
                    dialogState.value = false
                }) {
                Icon(
                    Icons.Filled.Close,
                    "contentDescription"
                )
            }
        }
        Divider(color = Color.DarkGray, thickness = 1.dp)
    }
}

@Composable
private fun BottomButtons(successButtonText: String, dialogState: MutableState<Boolean>) {
    Row(
        modifier = Modifier
            .fillMaxWidth(1f)
            .fillMaxWidth(1f)
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(100.dp)
                .padding(end = 5.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Cancel", fontSize = 20.sp)
        }
        Button(
            onClick = {
                dialogState.value = false
            },
            modifier = Modifier.width(100.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = successButtonText, fontSize = 20.sp)
        }

    }
}

@Composable
private fun AddBody(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .padding(20.dp)
    ) {
        content()
    }
}


@Composable
private fun AppBarAction(
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = null
        )
    }
}
