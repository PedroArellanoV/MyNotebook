package com.example.mynotebook.data.repository

import com.example.mynotebook.data.data_source.TaskDao
import com.example.mynotebook.data.data_source.model.TaskEntity
import com.example.mynotebook.domain.task.model.TaskModel
import com.example.mynotebook.domain.task.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(
    private val dao: TaskDao
): TaskRepository {
    override fun getTasks(): Flow<List<TaskEntity>> = dao.getTasks()

    override suspend fun getTaskById(id: Int): TaskEntity? = dao.getTaskById(id)

    override suspend fun insertTask(task: TaskEntity) = dao.insertTask(task)

    override suspend fun deleteTask(task: TaskEntity) = dao.deleteTask(task)
}