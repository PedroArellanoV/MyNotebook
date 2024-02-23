package com.example.mynotebook.domain.notes.use_cases

import android.util.Log
import com.example.mynotebook.domain.notes.model.InvalidNoteException
import com.example.mynotebook.domain.notes.model.NoteModel
import com.example.mynotebook.domain.notes.repository.NoteRepository

class AddNote(private val repository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: NoteModel) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("The title of the note can't be empty.")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("The content of the note can't be empty")
        }
        repository.insertNote(note)
        Log.d("note_recived", note.title)
    }
}