package com.example.mynotebook.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.mynotebook.domain.notes.model.NoteModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(): ViewModel() {

    private val _notesList = mutableStateOf<List<NoteModel>>(emptyList())
    val notesList: State<List<NoteModel>> = _notesList
}