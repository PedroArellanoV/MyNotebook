package com.example.mynotebook.domain.notes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_model")
data class NoteModel(
    val title: String,
    val content: String,
    val isFav: Boolean,
    @PrimaryKey val id: Int? = null
)


