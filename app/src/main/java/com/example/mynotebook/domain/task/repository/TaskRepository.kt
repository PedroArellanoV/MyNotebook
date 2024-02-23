package com.example.mynotebook.domain.task.repository

import com.example.mynotebook.domain.task.model.TaskModel
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<TaskModel>>

    suspend fun getTaskById(id: Int): TaskModel?

    suspend fun insertTask(task: TaskModel)

    suspend fun deleteTask(task: TaskModel)
}