package com.example.jounalapp.domain.usecase


import com.example.jounalapp.domain.model.Journal
import com.example.jounalapp.domain.repository.JournalRepository
import kotlinx.coroutines.flow.Flow

class GetJournalsUseCase(private val repository: JournalRepository) {
    operator fun invoke(): Flow<List<Journal>> = repository.getAllEntries()
}

class AddJournalUseCase(private val repository: JournalRepository) {
    suspend operator fun invoke(title: String, content: String) {
        if (title.isBlank() || content.isBlank()) return
        repository.addEntry(Journal(title = title.trim(), content = content.trim()))
    }
}

class DeleteJournalUseCase(private val repository: JournalRepository) {
    suspend operator fun invoke(journal: Journal) {
        repository.deleteEntry(journal)
    }
}