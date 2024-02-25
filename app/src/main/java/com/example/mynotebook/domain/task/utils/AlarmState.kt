package com.example.mynotebook.domain.task.utils

import com.example.mynotebook.presentation.add_edit_task.utils.DayOfWeek
import java.time.LocalDate

data class AlarmState(
    var isActive: Boolean,
    var typeOfAlarm: Alarm?
){
    sealed class Alarm{
        data class DailyAlarm(
            val selectedDays: List<DayOfWeek>,
            val hour: Int,
            val minute: Int
        ): Alarm()
        data class CalendarAlarm(
            val selectedDate: LocalDate,
            val hour: Int,
            val minute: Int
        ): Alarm()
    }
}