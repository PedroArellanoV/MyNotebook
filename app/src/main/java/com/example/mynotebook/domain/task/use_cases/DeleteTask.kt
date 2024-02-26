package com.example.mynotebook.domain.task.use_cases

import com.example.mynotebook.data.data_source.model.TaskEntity
import com.example.mynotebook.domain.task.repository.TaskRepository

class DeleteTask(private val repository: TaskRepository) {
    suspend operator fun invoke(task: TaskEntity) = repository.deleteTask(task)
}