package com.example.jounalapp.domain.model

data class Journal(
    val id: Int = 0,
    val title: String,
    val content: String,
    val dateAdded: Long = System.currentTimeMillis()
)
