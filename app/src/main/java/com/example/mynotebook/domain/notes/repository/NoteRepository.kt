package com.example.mynotebook.domain.notes.repository

import com.example.mynotebook.domain.notes.model.NoteModel
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<List<NoteModel>>

    suspend fun getNoteById(id: Int): NoteModel?

    suspend fun insertNote(note: NoteModel)

    suspend fun deleteNote(note: NoteModel)
}