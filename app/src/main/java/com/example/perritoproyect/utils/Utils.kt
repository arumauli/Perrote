package com.example.perritoproyect.utils

import com.example.perritoproyect.model.Dog
import com.example.perritoproyect.model.TransformationDog


fun convertListToAllDogs(list: Map<String, List<String>>?):List<String> {
    val dogs : ArrayList<String> = arrayListOf()
    list?.forEach { (s, strings) ->
        dogs.add(s)
        if (strings.isNotEmpty()){
            strings.forEach {
                dogs.add(s.plus("-".plus(it)))
            }
        }
    }
    return dogs
}

fun stringImageOrStringsImages(string: String):Boolean{
    return !string.contains("-")
}

fun stringsImagesTransformation(string: String): TransformationDog {
    val transformationDog = string.split("-")
    return TransformationDog(
        breeds = transformationDog.firstOrNull()?:"",
        hound = transformationDog.lastOrNull()?:""
    )
}

fun dogToDogEntity(dog: Dog):com.example.perritoproyect.entities.Dog{
    return com.example.perritoproyect.entities.Dog(name = dog.name, image = dog.image)

}



fun dogsEntityToDogs(perritos: List<com.example.perritoproyect.entities.Dog>):ArrayList<Dog>{
    val arrayList:ArrayList<Dog> = arrayListOf()
    perritos.forEach {
        arrayList.add(Dog(name = it.name, image = it.image))
    }
    return arrayList

}

fun dogEntityToDog(dog: com.example.perritoproyect.entities.Dog):Dog{
    return Dog(name = dog.name, image = dog.image, favorite = true)
}