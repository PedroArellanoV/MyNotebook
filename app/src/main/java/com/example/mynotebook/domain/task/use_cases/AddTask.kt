package com.example.mynotebook.domain.task.use_cases

import android.util.Log
import com.example.mynotebook.domain.task.model.TaskModel
import com.example.mynotebook.domain.task.repository.TaskRepository
import com.example.mynotebook.presentation.utils.InvalidCallException

class AddTask(private val repository: TaskRepository) {

    @Throws(InvalidCallException::class)
    suspend operator fun invoke(task: TaskModel) {
        if (task.title.isBlank()) {
            throw InvalidCallException("The title of the note can't be empty.")
        }
        repository.insertTask(task)
        Log.d("task_recived", task.title)
    }
}