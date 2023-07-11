package com.example.perritoproyect.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.perritoproyect.dao.PerritoDao
import com.example.perritoproyect.entities.Dog
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [Dog::class],
    version = 2
) abstract class PerritoRoomDatabase : RoomDatabase() {

    abstract fun PerritoDao(): PerritoDao companion object {

        @Volatile
        private var INSTANCE: PerritoRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ):PerritoRoomDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PerritoRoomDatabase::class.java,
                    "dog.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }


        }

    }
}