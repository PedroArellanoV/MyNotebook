package com.example.mynotebook.data.data_source.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mynotebook.domain.task.model.TaskModel
import com.example.mynotebook.domain.task.utils.AlarmState
import com.google.gson.Gson

@Entity(tableName = "task_model")
data class TaskEntity(
    val title: String,
    val description: String,
    val state: Boolean = false,
    val alarmState: String,
    @PrimaryKey val id: Int? = null
)

fun TaskEntity.toDomain(): TaskModel{
    return TaskModel(
        title = title,
        description = description,
        state = state,
        alarmState = Gson().fromJson(alarmState, AlarmState::class.java),
        id = id
    )
}