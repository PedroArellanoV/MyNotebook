package com.example.mynotebook.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mynotebook.domain.task.model.TaskModel

@Database(
    entities = [TaskModel::class],
    version = 2
)
@TypeConverters(Convertes::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao

    companion object {
        const val TASK_DATABASE_NAME = "task_db"
    }
}