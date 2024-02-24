package com.example.mynotebook.presentation.add_edit_notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotebook.domain.notes.model.NoteModel
import com.example.mynotebook.domain.notes.use_cases.NoteUseCases
import com.example.mynotebook.presentation.utils.InvalidCallException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _noteTitle = mutableStateOf("")
    val noteTitle: State<String> = _noteTitle

    private val _noteContent = mutableStateOf("")
    val noteContent: State<String> = _noteContent

    private val _noteFavState = mutableStateOf(false)
    val noteFavState: State<Boolean> = _noteFavState

    private val _eventFlow = MutableSharedFlow<NotesUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note ->
                        currentNoteId = note.id
                        _noteTitle.value = note.title
                        _noteContent.value = note.content
                    }
                }
            }
        }
    }

    fun onTitleChanged(newTitle: String) {
        _noteTitle.value = newTitle
    }

    fun onContentChanged(newContent: String) {
        _noteContent.value = newContent
    }

    fun onFavStateChanged(newState: Boolean) {
        _noteFavState.value = newState
    }

    fun onSaveNote() {
        viewModelScope.launch {
            try {
                noteUseCases.addNote(
                    NoteModel(
                        title = noteTitle.value,
                        content = noteContent.value,
                        id = currentNoteId
                    )
                )
                _eventFlow.emit(NotesUiEvent.SaveNote)
            } catch (e: InvalidCallException) {
                _eventFlow.emit(
                    NotesUiEvent.ShowSnackbar(
                        e.message ?: "Couldn't save note"
                    )
                )
            }
        }
    }


    fun onDeleteNote() {
        viewModelScope.launch {
            noteUseCases.deleteNote(
                NoteModel(
                    noteTitle.value,
                    noteContent.value,
                    currentNoteId
                )
            )
        }
    }

    sealed class NotesUiEvent {
        data class ShowSnackbar(val message: String) : NotesUiEvent()
        data object SaveNote : NotesUiEvent()
    }
}