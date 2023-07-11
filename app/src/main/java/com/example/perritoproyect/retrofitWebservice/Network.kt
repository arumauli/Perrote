package com.example.perritoproyect.retrofitWebservice

import com.example.perritoproyect.model.PerritoApiModelBreedImage
import com.example.perritoproyect.model.PerritoApiModelBreedImages
import com.example.perritoproyect.model.PerritoApiModelBreedsAll
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface BreedsAllService {
    @GET("api/breeds/list/all")
    suspend fun getBreedsAll(): PerritoApiModelBreedsAll
}

interface BreedImageService {
    @GET("api/breed/{breeds}/images/random")
    suspend fun getBreedImage(
        @Path("breeds") breeds:String
    ): PerritoApiModelBreedImage
}

interface BreedsImagesService {
    @GET("api/breed/{breeds}/{hound}/images/random/3")
    suspend fun getBreedsImages(
        @Path("breeds") breeds:String,
        @Path("hound") hound:String
    ): PerritoApiModelBreedImages
}

interface BreedsImageService {
    @GET("api/breed/{breeds}/{hound}/images/random")
    suspend fun getBreedsImage(
        @Path("breeds") breeds:String,
        @Path("hound") hound:String
    ): PerritoApiModelBreedImage
}

interface BreedImagesService {
    @GET("api/breed/{breeds}/images/random/3")
    suspend fun getBreedImages(
        @Path("breeds") breeds:String
    ): PerritoApiModelBreedImages
}

object RetrofitInstance {
    private const val BASE_URL = "https://dog.ceo"
    private val retrofit: Retrofit by lazy {

        val levelType = HttpLoggingInterceptor.Level.BODY
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor()
                        .setLevel(levelType)
                ).build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val perritoBreedsAllService: BreedsAllService by lazy {
        retrofit.create(BreedsAllService::class.java)
    }

    val perritoBreedImageService: BreedImageService by lazy {
        retrofit.create(BreedImageService::class.java)
    }

    val perritoBreedsImagesService: BreedsImagesService by lazy {
        retrofit.create(BreedsImagesService::class.java)
    }

    val perritoBreedsImageService: BreedsImageService by lazy {
        retrofit.create(BreedsImageService::class.java)
    }



    val perritoBreedImagesService: BreedImagesService by lazy {
        retrofit.create(BreedImagesService::class.java)
    }

}