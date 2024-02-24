package com.example.mynotebook.domain.notes.use_cases

import android.util.Log
import com.example.mynotebook.domain.notes.model.NoteModel
import com.example.mynotebook.domain.notes.repository.NoteRepository
import com.example.mynotebook.presentation.utils.InvalidCallException

class AddNote(private val repository: NoteRepository) {

    @Throws(InvalidCallException::class)
    suspend operator fun invoke(note: NoteModel) {
        if (note.title.isBlank()) {
            throw InvalidCallException("The title of the note can't be empty.")
        }
        if (note.content.isBlank()) {
            throw InvalidCallException("The content of the note can't be empty")
        }
        repository.insertNote(note)
        Log.d("note_recived", note.title)
    }
}