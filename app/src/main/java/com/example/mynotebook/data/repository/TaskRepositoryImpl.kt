package com.example.mynotebook.data.repository

import com.example.mynotebook.data.data_source.TaskDao
import com.example.mynotebook.domain.task.model.TaskModel
import com.example.mynotebook.domain.task.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val dao: TaskDao
): TaskRepository {
    override fun getTasks(): Flow<List<TaskModel>> = dao.getTasks()

    override suspend fun getTaskById(id: Int): TaskModel? = dao.getTaskById(id)

    override suspend fun insertTask(task: TaskModel) = dao.insertTask(task)

    override suspend fun deleteTask(task: TaskModel) = dao.deleteTask(task)
}