package com.example.perritoproyect.model

data class PerritoApiModelBreedsAll(
    val message: Map<String, List<String>>,
    val status: String,
)


data class PerritoApiModelBreedImage(
    val message: String,
    val status: String,
)

data class PerritoApiModelBreedImages(
    val message: List<String>,
    val status: String,
)