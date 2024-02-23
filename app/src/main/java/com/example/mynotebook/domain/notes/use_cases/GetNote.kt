package com.example.mynotebook.domain.notes.use_cases

import com.example.mynotebook.domain.notes.repository.NoteRepository

class GetNote(private val repository: NoteRepository) {
    suspend operator fun invoke(noteId: Int) = repository.getNoteById(noteId)
}