package com.example.perritoproyect.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.perritoproyect.entities.Dog
import kotlinx.coroutines.flow.Flow

@Dao
interface PerritoDao {
    @Insert()
    fun insert(user: Dog)

    @Query("SELECT * FROM perros")
    fun getAll(): Flow<List<Dog>>
    @Query("SELECT * FROM perros WHERE name = :name limit 1")
    fun findByName(name: String,): Dog?
    @Query("Delete from perros WHERE name = :name ")
    suspend fun delete(name: String)
}