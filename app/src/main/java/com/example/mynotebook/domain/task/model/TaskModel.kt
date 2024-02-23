package com.example.mynotebook.domain.task.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_model")
data class TaskModel(
    val title: String,
    val description: String,
    val state: Boolean,
    @PrimaryKey val id: Int? = null
)

