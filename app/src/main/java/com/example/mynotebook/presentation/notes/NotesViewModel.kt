package com.example.mynotebook.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotebook.domain.notes.model.NoteModel
import com.example.mynotebook.domain.notes.use_cases.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
): ViewModel() {
    private var _noteList = mutableStateOf<List<NoteModel>>(emptyList())
    val noteList: State<List<NoteModel>> = _noteList

    fun getNotes(){
        viewModelScope.launch {
            noteUseCases.getNotes.invoke().collect {
                _noteList.value = it
            }

        }
    }

    fun onFavChange(item: NoteModel){
        val newItem = NoteModel(
            title = item.title,
            content = item.content,
            isFav = !item.isFav,
            id = item.id
        )
        viewModelScope.launch {
            noteUseCases.addNote.invoke(newItem)
        }
    }
}