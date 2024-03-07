package com.example.mynotebook.domain.task.utils

import android.os.Parcelable
import java.time.LocalDate

data class AlarmState(
    var isActive: Boolean,
    var dailyAlarm: DailyAlarm?,
    var calendarAlarm: CalendarAlarm?
): Parcelable

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