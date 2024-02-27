package com.example.mynotebook.domain.task.utils

import java.time.LocalDate

data class AlarmState(
    var isActive: Boolean,
    var dailyAlarm: DailyAlarm?,
    var calendarAlarm: CalendarAlarm?
)

data class DailyAlarm(
    var selectedDays: List<String>,
    var hour: Int,
    var minute: Int
)

data class CalendarAlarm(
    var selectedDate: LocalDate,
    var hour: Int,
    var minute: Int
)