package com.example.mynotebook.presentation.add_edit_task

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mynotebook.TaskNotification
import com.example.mynotebook.TaskNotification.Companion.NOTIFICATION_ID
import com.example.mynotebook.TaskNotification.Companion.NOTIFICATION_TEXT
import com.example.mynotebook.TaskNotification.Companion.NOTIFICATION_TITLE
import com.example.mynotebook.data.data_source.model.toDomain
import com.example.mynotebook.domain.task.model.TaskModel
import com.example.mynotebook.domain.task.model.toTaskEntity
import com.example.mynotebook.domain.task.use_cases.TaskUseCases
import com.example.mynotebook.domain.task.utils.AlarmState
import com.example.mynotebook.domain.task.utils.DailyAlarm
import com.example.mynotebook.presentation.add_edit_task.utils.DayOfWeek
import com.example.mynotebook.presentation.add_edit_task.utils.Options
import com.example.mynotebook.presentation.utils.InvalidCallException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskUseCases: TaskUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _taskTitle = mutableStateOf("")
    val taskTitle: State<String> = _taskTitle

    private val _taskDescription = mutableStateOf("")
    val taskDescription: State<String> = _taskDescription

    private val _typeOfAlarm = mutableStateOf(Options.DAILY)
    val typeOfAlarm: State<Options> = _typeOfAlarm

    private val _selectedDays = mutableStateOf<List<DayOfWeek>>(emptyList())
    val selectedDays: State<List<DayOfWeek>> = _selectedDays

    private val _eventFlow = MutableSharedFlow<TaskUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTaskId: Int? = null

    private val _alarmPermission = mutableStateOf(false)
    val alarmPermission: State<Boolean> = _alarmPermission

    private val _alarmState = mutableStateOf(
        AlarmState(
            isActive = false,
            dailyAlarm = null,
            calendarAlarm = null
        )
    )
    val alarmState: State<AlarmState> = _alarmState


    private val _selectedHour = mutableStateOf(alarmState.value.dailyAlarm?.hour ?: 12)
    val selectedHour: State<Int> = _selectedHour

    private val _selectedMinute = mutableStateOf(alarmState.value.dailyAlarm?.minute ?: 0)
    val selectedMinute: State<Int> = _selectedMinute

    init {
        savedStateHandle.get<Int>("taskId")?.let { taskId ->
            if (taskId != -1) {
                viewModelScope.launch {
                    taskUseCases.getTaskById(taskId)?.also { task ->
                        currentTaskId = task.id
                        _taskTitle.value = task.title
                        _taskDescription.value = task.description
                        _alarmState.value = task.toDomain().alarmState
                    }
                    if (alarmState.value.dailyAlarm != null) {
                        _typeOfAlarm.value = Options.DAILY
                        alarmState.value.dailyAlarm!!.selectedDays.forEach {
                            daysFromDb(it)
                        }
                        _selectedHour.value = alarmState.value.dailyAlarm!!.hour
                        _selectedMinute.value = alarmState.value.dailyAlarm!!.minute
                        Log.d("recuperar_alarma", alarmState.value.dailyAlarm.toString())
                    } else if (alarmState.value.calendarAlarm != null) {
                        _typeOfAlarm.value = Options.CALENDAR
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

    fun onAlarmTypeChange(index: Int) {
        when (index) {
            0 -> _typeOfAlarm.value = Options.DAILY
            1 -> _typeOfAlarm.value = Options.CALENDAR
        }
    }

    fun toggledAlarmSwitch(newState: Boolean) {
        _alarmState.value.isActive = newState
    }

    fun onSaveTask() {
        viewModelScope.launch {
            try {
                taskUseCases.addTask(
                    TaskModel(
                        title = taskTitle.value,
                        description = taskDescription.value,
                        alarmState = alarmState.value,
                        id = currentTaskId
                    ).toTaskEntity()
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

    fun onDeleteTask() {
        viewModelScope.launch {
            taskUseCases.deleteTask(
                TaskModel(
                    taskTitle.value,
                    taskDescription.value,
                    alarmState.value,
                    currentTaskId
                ).toTaskEntity()
            )
        }
    }

    fun onDailyAlarmSave(
        timePickerState: TimePickerState
    ) {
        _alarmState.value.dailyAlarm = DailyAlarm(
            selectedDays = selectedDays.value.map {
                it.abbreviation
            },
            hour = timePickerState.hour,
            minute = timePickerState.minute,
        )
        Log.d("alarm_saved", "${alarmState.value.dailyAlarm}")
    }

    fun onCalendarAlarmSave(
        datePickerState: DatePickerState,
        context: Context
    ) {
        val intent = Intent(context.applicationContext, TaskNotification::class.java)
        intent.putExtra(NOTIFICATION_TITLE, taskTitle.value)
        intent.putExtra(NOTIFICATION_TEXT, taskDescription.value)

        val pendingIntent = PendingIntent.getBroadcast(
            context.applicationContext,
            NOTIFICATION_ID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = datePickerState.selectedDateMillis
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    time!!,
                    pendingIntent
                )
            } else {
                _alarmPermission.value = true
            }

        } else {

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time!!,
                pendingIntent
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

    private fun daysFromDb(day: String) {
        when (day) {
            DayOfWeek.MON.abbreviation -> {
                toggleDaySelection(DayOfWeek.MON)
            }

            DayOfWeek.TUE.abbreviation -> {
                toggleDaySelection(DayOfWeek.TUE)
            }

            DayOfWeek.WED.abbreviation -> {
                toggleDaySelection(DayOfWeek.WED)
            }

            DayOfWeek.THU.abbreviation -> {
                toggleDaySelection(DayOfWeek.THU)
            }

            DayOfWeek.FRI.abbreviation -> {
                toggleDaySelection(DayOfWeek.FRI)
            }

            DayOfWeek.SAT.abbreviation -> {
                toggleDaySelection(DayOfWeek.SAT)
            }

            DayOfWeek.SUN.abbreviation -> {
                toggleDaySelection(DayOfWeek.SUN)
            }

        }
    }

    sealed class TaskUiEvent {
        data class ShowSnackbar(val message: String) : TaskUiEvent()
        data object SaveTask : TaskUiEvent()
    }

}