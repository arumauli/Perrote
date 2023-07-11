package com.example.perritoproyect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.perritoproyect.database.PerritoRoomDatabase
import com.example.perritoproyect.navigation.Navigation
import com.example.perritoproyect.repository.PerritoRoomRepository
import com.example.perritoproyect.viewModel.PerritoViewModelFactory
import com.example.perritoproyect.viewModel.ViewModelDog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {

    val applicationScope = CoroutineScope(Dispatchers.IO)

    val database by lazy { PerritoRoomDatabase.getDatabase(
        context = this,
        scope = applicationScope)
    }

    val repository by lazy { PerritoRoomRepository(database.PerritoDao()) }

    private val viewModelDog by viewModels<ViewModelDog>{
        PerritoViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Navigation(viewModelDog)

        }
    }
}

