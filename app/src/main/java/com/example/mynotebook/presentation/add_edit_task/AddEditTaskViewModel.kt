package com.example.mynotebook.presentation.add_edit_task

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotebook.domain.task.model.TaskModel
import com.example.mynotebook.domain.task.use_cases.DeleteTask
import com.example.mynotebook.domain.task.use_cases.TaskUseCases
import com.example.mynotebook.domain.task.utils.AlarmState
import com.example.mynotebook.presentation.add_edit_task.utils.DayOfWeek
import com.example.mynotebook.presentation.utils.InvalidCallException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _taskTitle = mutableStateOf("")
    val taskTitle: State<String> = _taskTitle

    private val _taskDescription = mutableStateOf("")
    val taskDescription: State<String> = _taskDescription

    private val _taskState = mutableStateOf(false)
    val taskState: State<Boolean> = _taskState


    private val _selectedDays = mutableStateOf<List<DayOfWeek>>(emptyList())
    val selectedDays: State<List<DayOfWeek>> = _selectedDays

    private val _eventFlow = MutableSharedFlow<TaskUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTaskId: Int? = null

    private val _alarmState = mutableStateOf(
        AlarmState(
            isActive = false,
            time = null
        )
    )
    val alarmState: State<AlarmState> = _alarmState


    init {
        savedStateHandle.get<Int>("taskId")?.let { taskId ->
            if (taskId != -1) {
                viewModelScope.launch {
                    taskUseCases.getTaskById(taskId)?.also { task ->
                        currentTaskId = task.id
                        _taskTitle.value = task.title
                        _taskDescription.value = task.description
                        _taskState.value = task.state
                    }
                }
            }
        }
    }


    fun onTitleChanged(newTitle: String) {
        _taskTitle.value = newTitle
    }

    fun onDescriptionChanged(newDescription: String) {
        _taskDescription.value = newDescription
    }

    fun onStateChanged(newState: Boolean) {
        _taskState.value = newState
    }

    fun addAlarmToTask(hour: Int, minute: Int) {
        _alarmState.value = AlarmState(
            isActive = true,
            time = "$hour, $minute"
        )
    }

    fun onAlarmChanged(newState: Boolean){
        _alarmState.value.isActive = newState
    }

    fun onSaveTask() {
        viewModelScope.launch {
            try {
                taskUseCases.addTask(
                    TaskModel(
                        title = taskTitle.value,
                        description = taskDescription.value,
                        state = taskState.value,
                        alarmState = alarmState.value
                    )
                )
                _eventFlow.emit(TaskUiEvent.SaveTask)
            } catch (e: InvalidCallException) {
                _eventFlow.emit(
                    TaskUiEvent.ShowSnackbar(
                        e.message ?: "Couldn't save task"
                    )
                )
            }
        }
    }

    fun onDeleteTask(){
        viewModelScope.launch {
            taskUseCases.deleteTask(
                TaskModel(
                    taskTitle.value,
                    taskDescription.value,
                    taskState.value,
                    alarmState.value,
                    currentTaskId
                )
            )
        }
    }

    fun toggleDaySelection(day: DayOfWeek) {
        _selectedDays.value = if (_selectedDays.value.contains(day)) {
            _selectedDays.value - day
        } else {
            _selectedDays.value + day
        }
    }

    sealed class TaskUiEvent {
        data class ShowSnackbar(val message: String) : TaskUiEvent()
        data object SaveTask : TaskUiEvent()
    }

}