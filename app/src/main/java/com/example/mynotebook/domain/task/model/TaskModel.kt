package com.example.mynotebook.domain.task.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mynotebook.domain.task.utils.AlarmState

@Entity(tableName = "task_model")
data class TaskModel(
    val title: String,
    val description: String,
    val state: Boolean = false,
    val alarmState: AlarmState,
    @PrimaryKey val id: Int? = null
)

