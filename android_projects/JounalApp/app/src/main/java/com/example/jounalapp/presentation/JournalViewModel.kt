package com.example.jounalapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.jounalapp.domain.model.Journal
import com.example.jounalapp.domain.usecase.AddJournalUseCase
import com.example.jounalapp.domain.usecase.DeleteJournalUseCase
import com.example.jounalapp.domain.usecase.GetJournalsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class JournalViewModel(
    getJournalsUseCase: GetJournalsUseCase,
    private val addJournalUseCase: AddJournalUseCase,
    private val deleteJournalUseCase: DeleteJournalUseCase
) : ViewModel() {

    val entries: StateFlow<List<Journal>> = getJournalsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addEntry(title: String, content: String) {
        viewModelScope.launch {
            addJournalUseCase(title, content)
        }
    }

    fun deleteEntry(journal: Journal) {
        viewModelScope.launch {
            deleteJournalUseCase(journal)
        }
    }
}

class JournalViewModelFactory(
    private val getJournalsUseCase: GetJournalsUseCase,
    private val addJournalUseCase: AddJournalUseCase,
    private val deleteJournalUseCase: DeleteJournalUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JournalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JournalViewModel(
                getJournalsUseCase,
                addJournalUseCase,
                deleteJournalUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}