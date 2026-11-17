package com.example.jounalapp.domain.repository

import com.example.jounalapp.domain.model.Journal
import kotlinx.coroutines.flow.Flow

interface JournalRepository {
    fun getAllEntries(): Flow<List<Journal>>
    suspend fun addEntry(journal: Journal)
    suspend fun deleteEntry(journal: Journal)
}