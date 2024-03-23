package com.example.mynotebook

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat



class TaskNotification: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notification: Notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(NOTIFICATION_TEXT)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)
    }

    companion object{
        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "taskChannel"
        const val NOTIFICATION_TITLE = "title"
        const val NOTIFICATION_TEXT = "text"
    }

}