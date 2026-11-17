package com.example.jounalapp.data.local

import androidx.room.*
import com.example.journalapp.data.local.JournalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JournalDao {
    @Query("SELECT * FROM journal_entries ORDER BY dateAdded DESC")
    fun getAllEntries(): Flow<List<JournalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntry(entry: JournalEntity)

    @Delete
    suspend fun deleteEntry(entry: JournalEntity)
}