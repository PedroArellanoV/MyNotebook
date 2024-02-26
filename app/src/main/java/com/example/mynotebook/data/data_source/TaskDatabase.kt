package com.example.mynotebook.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mynotebook.data.data_source.model.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 2
)
abstract class TaskDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao

    companion object {
        const val TASK_DATABASE_NAME = "task_db"
    }
}