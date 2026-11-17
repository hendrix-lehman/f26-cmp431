package com.example.jounalapp.data.repository

import com.example.jounalapp.data.local.JournalDao
import com.example.jounalapp.domain.model.Journal
import com.example.jounalapp.domain.repository.JournalRepository
import com.example.journalapp.data.mapper.toDomain
import com.example.journalapp.data.mapper.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class JournalRepositoryImpl(
    private val dao: JournalDao
) : JournalRepository {

    override fun getAllEntries(): Flow<List<Journal>> {
        return dao.getAllEntries().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addEntry(journal: Journal) {
        dao.insertEntry(journal.toEntity())
    }

    override suspend fun deleteEntry(journal: Journal) {
        dao.deleteEntry(journal.toEntity())
    }
}