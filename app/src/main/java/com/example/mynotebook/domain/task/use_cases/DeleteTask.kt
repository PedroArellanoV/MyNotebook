package com.example.mynotebook.domain.task.use_cases

import com.example.mynotebook.domain.task.model.TaskModel
import com.example.mynotebook.domain.task.repository.TaskRepository

class DeleteTask(private val repository: TaskRepository) {
    suspend operator fun invoke(task: TaskModel) = repository.deleteTask(task)
}