package com.example.mynotebook.domain.task.use_cases

import com.example.mynotebook.domain.task.repository.TaskRepository

class GetTaskById(private val repository: TaskRepository) {
    suspend operator fun invoke(id: Int) = repository.getTaskById(id)
}