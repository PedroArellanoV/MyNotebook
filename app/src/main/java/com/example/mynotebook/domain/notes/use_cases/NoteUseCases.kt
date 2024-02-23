package com.example.mynotebook.domain.notes.use_cases

data class NoteUseCases(
    val getNotes: GetNotes,
    val getNote: GetNote,
    val addNote: AddNote,
    val deleteNote: DeleteNote
)