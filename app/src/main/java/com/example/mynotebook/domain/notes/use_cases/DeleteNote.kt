package com.example.mynotebook.domain.notes.use_cases

import com.example.mynotebook.domain.notes.model.NoteModel
import com.example.mynotebook.domain.notes.repository.NoteRepository

class DeleteNote(private val repository: NoteRepository) {
    suspend operator fun invoke(note: NoteModel) = repository.deleteNote(note)
}