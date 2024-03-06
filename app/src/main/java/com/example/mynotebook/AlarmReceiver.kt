package com.example.mynotebook

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.mynotebook.domain.task.model.TaskModel

class AlarmReceiver(val task: TaskModel): BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        TaskNotificationService(context).showNotification(task)
    }
}