package com.example.journalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.jounalapp.data.local.JournalDatabase
import com.example.jounalapp.data.repository.JournalRepositoryImpl
import com.example.jounalapp.domain.usecase.AddJournalUseCase
import com.example.jounalapp.domain.usecase.DeleteJournalUseCase
import com.example.jounalapp.domain.usecase.GetJournalsUseCase
import com.example.jounalapp.presentation.JournalScreen
import com.example.jounalapp.presentation.JournalViewModel
import com.example.jounalapp.presentation.JournalViewModelFactory

import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Data Layer
        val database = JournalDatabase.getDatabase(applicationContext)
        val repository = JournalRepositoryImpl(database.journalDao())

        // 2. Domain Layer (Use Cases)
        val getJournals = GetJournalsUseCase(repository)
        val addJournal = AddJournalUseCase(repository)
        val deleteJournal = DeleteJournalUseCase(repository)

        // 3. Presentation Layer (Factory)
        val factory = JournalViewModelFactory(getJournals, addJournal, deleteJournal)

        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val viewModel: JournalViewModel = viewModel(factory = factory)
                    JournalScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}