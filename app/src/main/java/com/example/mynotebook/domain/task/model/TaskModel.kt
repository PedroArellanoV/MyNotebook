package com.example.mynotebook.domain.task.model

import com.example.mynotebook.data.data_source.model.TaskEntity
import com.example.mynotebook.domain.task.utils.AlarmState
import com.google.gson.Gson

data class TaskModel(
    val title: String,
    val description: String,
    val state: Boolean = false,
    val alarmState: AlarmState,
    val id: Int? = null
)

fun TaskModel.toTaskEntity(): TaskEntity {
    return TaskEntity(
        title = title,
        description = description,
        state = state,
        alarmState = Gson().toJson(alarmState),
        id = id
    )
}