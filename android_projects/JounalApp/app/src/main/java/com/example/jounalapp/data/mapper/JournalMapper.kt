package com.example.journalapp.data.mapper

import com.example.jounalapp.domain.model.Journal
import com.example.journalapp.data.local.JournalEntity

fun JournalEntity.toDomain(): Journal = Journal(
    id = id,
    title = title,
    content = content,
    dateAdded = dateAdded
)

fun Journal.toEntity(): JournalEntity = JournalEntity(
    id = id,
    title = title,
    content = content,
    dateAdded = dateAdded
)