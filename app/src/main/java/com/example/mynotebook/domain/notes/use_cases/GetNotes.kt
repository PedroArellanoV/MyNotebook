package com.example.mynotebook.domain.notes.use_cases

import com.example.mynotebook.domain.notes.model.NoteModel
import com.example.mynotebook.domain.notes.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class GetNotes(private val repository: NoteRepository) {
    operator fun invoke(): Flow<List<NoteModel>> =  repository.getNotes()
}