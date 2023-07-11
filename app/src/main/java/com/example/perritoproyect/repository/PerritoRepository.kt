package com.example.perritoproyect.repository

import androidx.annotation.WorkerThread
import com.example.perritoproyect.dao.PerritoDao
import com.example.perritoproyect.entities.Dog
import com.example.perritoproyect.model.PerritoApiModelBreedImage
import com.example.perritoproyect.model.PerritoApiModelBreedImages
import com.example.perritoproyect.model.PerritoApiModelBreedsAll
import com.example.perritoproyect.retrofitWebservice.RetrofitInstance
import kotlinx.coroutines.flow.Flow


class PerritoRepository {
    private val perritoBreedsAllService = RetrofitInstance.perritoBreedsAllService
    private val perritoBreedImageService = RetrofitInstance.perritoBreedImageService
    private val perritoBreedImagesService = RetrofitInstance.perritoBreedImagesService
    private val perritoBreedsImagesService = RetrofitInstance.perritoBreedsImagesService
    private val perritoBreedsImageService = RetrofitInstance.perritoBreedsImageService


    suspend fun getBreedsAll(): PerritoApiModelBreedsAll {
        return perritoBreedsAllService.getBreedsAll()
    }

    suspend fun getperritoBreedImageService(breeds:String): PerritoApiModelBreedImage {
        return perritoBreedImageService.getBreedImage(breeds = breeds)
    }

    suspend fun getdogBreedsImageService(breeds:String, hound:String): PerritoApiModelBreedImage {
        return perritoBreedsImageService.getBreedsImage(breeds = breeds, hound=hound)
    }

    suspend fun getperritoBreedsImagesService(breeds:String, hound:String): PerritoApiModelBreedImages {
        return perritoBreedsImagesService.getBreedsImages(breeds = breeds, hound=hound)
    }
    suspend fun getperritoBreedImagesService(breeds:String): PerritoApiModelBreedImages {
        return perritoBreedImagesService.getBreedImages(breeds = breeds)
    }
}

class PerritoRoomRepository(private val perrinDao: PerritoDao) {

    val allDogs: Flow<List<Dog>> = perrinDao.getAll()

    @WorkerThread
    fun insert(dog: Dog){
        perrinDao.insert(dog)
    }
    @WorkerThread
    fun findByName(name: String):Dog?{
        return perrinDao.findByName(name)
    }

    suspend fun delete(name: String){
        perrinDao.delete(name)
    }
}