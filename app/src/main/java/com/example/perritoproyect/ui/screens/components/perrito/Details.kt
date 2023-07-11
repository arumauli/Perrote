package com.example.perritoproyect.ui.screens.components.perrito

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage

@Composable
fun Detail(images:List<String>?){
    LazyColumn(modifier = Modifier
        .fillMaxHeight()){
        items(images?: arrayListOf()){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                AsyncImage(
                    model = it,
                    contentDescription = it,
                    contentScale = ContentScale.None,
                )
            }
        }
    }

}

@Preview
@Composable
fun PreviewDetail(){
    Detail(arrayListOf("https://images.dog.ceo/breeds/boxer/n02108089_5599.jpg"))
}