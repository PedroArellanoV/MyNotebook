package com.example.mynotebook.presentation.tasks

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotebook.data.data_source.model.toDomain
import com.example.mynotebook.domain.task.model.TaskModel
import com.example.mynotebook.domain.task.model.toTaskEntity
import com.example.mynotebook.domain.task.use_cases.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskScreenViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases
) : ViewModel() {
    private val _taskList = mutableStateOf<List<TaskModel>>(emptyList())
    val taskList: State<List<TaskModel>> = _taskList

    private val _taskTimer = mutableStateOf("")
    val taskAlarm = _taskTimer

    private val _refreshKey = mutableStateOf(0)
    val refreshKey = _refreshKey

    fun getTasks() {
        viewModelScope.launch {
            taskUseCases.getTasks.invoke().collect {
                _taskList.value = it.map { task ->
                    task.toDomain()
                }
            }
        }
    }

    fun onStateChanged(task: TaskModel) {
        task.alarmState.isActive = !task.alarmState.isActive
        val newItem = TaskModel(
            title = task.title,
            description = task.description,
            id = task.id,
            alarmState = task.alarmState
        )
        viewModelScope.launch {
            taskUseCases.addTask.invoke(newItem.toTaskEntity())
            _refreshKey.value++
        }
    }

    fun getReminder(item: TaskModel): String {
        val minute: String = if (item.alarmState.dailyAlarm!!.minute == 0) {
            "00"
        } else {
            item.alarmState.dailyAlarm!!.minute.toString()
        }

        return if (item.alarmState.isActive && item.alarmState.dailyAlarm != null) {
            "${item.alarmState.dailyAlarm!!.hour}:$minute hs"
        } else if (item.alarmState.isActive && item.alarmState.calendarAlarm != null) {
            ""
        } else {
            ""
        }
    }

    fun deleteTask(task: TaskModel) {
        viewModelScope.launch {
            taskUseCases.deleteTask.invoke(task.toTaskEntity())
        }
    }
}