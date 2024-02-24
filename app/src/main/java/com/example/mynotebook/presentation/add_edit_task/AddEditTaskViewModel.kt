package com.example.mynotebook.presentation.add_edit_task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.mynotebook.domain.task.use_cases.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _taskTitle = mutableStateOf("")
    val taskTitle: State<String> = _taskTitle

    private val _taskState = mutableStateOf(false)
    val taskState: State<Boolean> = _taskState


    fun onTitleChanged(newTitle: String){
        _taskTitle.value = newTitle
    }

    fun onStateChanged(newState: Boolean){
        _taskState.value = newState
    }
}