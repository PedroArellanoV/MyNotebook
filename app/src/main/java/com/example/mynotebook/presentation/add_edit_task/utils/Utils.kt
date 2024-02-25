package com.example.mynotebook.presentation.add_edit_task.utils


enum class DayOfWeek(val abbreviation: String) {
    MON("M"),
    TUE("T"),
    WED("W"),
    THU("Th"),
    FRI("F"),
    SAT("S"),
    SUN("Su")
}

enum class Options(val options: String, val index: Int){
    DAILY("Daily", 0),
    CALENDAR("Calendar", 1)
}