package com.example.mynotebook.presentation.tasks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotebook.data.data_source.model.TaskEntity
import com.example.mynotebook.data.data_source.model.toDomain
import com.example.mynotebook.domain.task.model.TaskModel
import com.example.mynotebook.domain.task.use_cases.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskScreenViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases
): ViewModel(){
    private var _taskList = mutableStateOf<List<TaskModel>>(emptyList())
    val taskList: State<List<TaskModel>> = _taskList

    fun getTasks(){
        viewModelScope.launch{
            taskUseCases.getTasks.invoke().collect{
                _taskList.value = it.map {
                    it.toDomain()
                }
            }
        }
    }

    fun onStateChanged(task: TaskEntity){
        val newItem = TaskEntity(
            title = task.title,
            description = task.description,
            id = task.id,
            state = !task.state,
            alarmState = task.alarmState
        )
        viewModelScope.launch {
            taskUseCases.addTask.invoke(newItem)
        }
    }
}