package com.example.mynotebook.presentation.add_edit_notes

data class NoteTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)