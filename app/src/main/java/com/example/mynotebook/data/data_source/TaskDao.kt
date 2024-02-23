package com.example.mynotebook.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynotebook.domain.task.model.TaskModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_model")
    fun getTasks(): Flow<List<TaskModel>>

    @Query("SELECT * FROM task_model WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskModel)

    @Delete
    suspend fun deleteTask(task: TaskModel)
}