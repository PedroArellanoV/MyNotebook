package com.example.mynotebook

import com.example.mynotebook.domain.task.model.TaskModel

interface AlarmScheduler {
    fun schedule(item: TaskModel)
    fun cancel(item: TaskModel)
}