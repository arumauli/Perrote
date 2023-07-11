package com.example.perritoproyect.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.perritoproyect.model.Dog
import com.example.perritoproyect.repository.PerritoRepository
import com.example.perritoproyect.repository.PerritoRoomRepository
import com.example.perritoproyect.utils.convertListToAllDogs
import com.example.perritoproyect.utils.dogToDogEntity
import com.example.perritoproyect.utils.dogsEntityToDogs
import com.example.perritoproyect.utils.stringImageOrStringsImages
import com.example.perritoproyect.utils.stringsImagesTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ViewModelDog(var roomRepository: PerritoRoomRepository) : ViewModel() {

    private val repository = PerritoRepository()


    private val _perritos = MutableLiveData<List<String>>()
    val dogs: LiveData<List<String>?> = _perritos


    private val _dogsHome = MutableLiveData<ArrayList<Dog>>()
    val dogsHome: LiveData<ArrayList<Dog>?> = _dogsHome

    private val _dogsRoom = MutableLiveData<ArrayList<Dog>>()
    val dogsRoom: LiveData<ArrayList<Dog>?> = _dogsRoom

    private val _dog = MutableLiveData<Dog>()
    val dog: LiveData<Dog> = _dog


    private val _dogsDetail = MutableLiveData<List<String>>()
    val dogsDetail: LiveData<List<String>> = _dogsDetail


    fun getBreedsAll() {
        viewModelScope.launch {
            try {
                val dogs = repository.getBreedsAll()
                _perritos.value = convertListToAllDogs(dogs.message)
                getDogHome()
            }catch (e: Exception) {
                Log.e("getBreedsAll", e.message.toString());
            }
        }
    }

    fun getDogBreedImages(breeds:String){

        viewModelScope.launch {
            try {
                if (stringImageOrStringsImages(breeds)){
                    val images = repository.getperritoBreedImagesService(breeds).message
                    _dogsDetail.value = images
                }else{
                    val transformation = stringsImagesTransformation(breeds)
                    val images = repository.getperritoBreedsImagesService(transformation.breeds,transformation.hound).message
                    _dogsDetail.value = images
                }

            }catch (e: Exception) {
                Log.e("getPerritoBreedImages", e.message.toString());
            }

        }
    }

    private fun getDogHome() {
        val dogsHome = arrayListOf<Dog>()
        viewModelScope.launch {
            try {
                dogs.value?.shuffled()?.take(5)?.forEach {
                    if (stringImageOrStringsImages(it)){
                        val dog = repository.getperritoBreedImageService(it)
                        dogsHome.add(Dog(name = it, image = dog.message))
                    }else{
                        val transformation = stringsImagesTransformation(it)
                        val dog = repository.getdogBreedsImageService(breeds = transformation.breeds, hound = transformation.hound)
                        dogsHome.add(Dog(name = it, image = dog.message))
                    }

                }
                _dogsHome.value = dogsHome
            }catch (e: Exception) {
                Log.e("getDogHome", e.message.toString());
            }
        }
    }

    fun getRoomAllDogs(){
        viewModelScope.launch(Dispatchers.Main) {
            val data = roomRepository.allDogs.first()
            _dogsRoom.value = dogsEntityToDogs(data)
        }

    }

    fun insertRoomDog(dog: Dog){
        viewModelScope.launch(Dispatchers.IO){
            roomRepository.insert(dogToDogEntity(dog))
        }
        getRoomAllDogs()
    }

    fun deleteRoomDog(dog: Dog){
        viewModelScope.launch(Dispatchers.IO){
            roomRepository.delete(dog.name)
        }
        getRoomAllDogs()
    }
}

class PerritoViewModelFactory(private val repository: PerritoRoomRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModelDog::class.java)) {
            return ViewModelDog(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}