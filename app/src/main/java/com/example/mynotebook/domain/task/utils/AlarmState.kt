package com.example.mynotebook.domain.task.utils

import java.time.LocalDate

data class AlarmState(
    var isActive: Boolean,
    var dailyAlarm: DailyAlarm?,
    var calendarAlarm: CalendarAlarm?
)

data class DailyAlarm(
    val selectedDays: List<DayOfWeek>,
    val hour: Int,
    val minute: Int
)

data class CalendarAlarm(
    val selectedDate: LocalDate,
    val hour: Int,
    val minute: Int
)

data class DayOfWeek(
    val day: String,
    val active: Boolean
)