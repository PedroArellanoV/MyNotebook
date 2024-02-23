package com.example.mynotebook.domain.task.use_cases

import com.example.mynotebook.domain.task.model.TaskModel
import com.example.mynotebook.domain.task.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasks(private val repository: TaskRepository) {
    operator fun invoke(): Flow<List<TaskModel>> = repository.getTasks()
}