package com.example.mynotebook.domain.task.use_cases

data class TaskUseCases(
    val getTasks: GetTasks,
    val addTask: AddTask,
    val deleteTask: DeleteTask,
    val getTaskById: GetTaskById
)