package com.example.mynotebook.domain.task.repository

import com.example.mynotebook.data.data_source.model.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<TaskEntity>>

    suspend fun getTaskById(id: Int): TaskEntity?

    suspend fun insertTask(task: TaskEntity)

    suspend fun deleteTask(task: TaskEntity)
}