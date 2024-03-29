package com.example.mynotebook

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyNotebookApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            TaskNotification.CHANNEL_ID,
            "Tasks",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Tasks reminders"

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
}