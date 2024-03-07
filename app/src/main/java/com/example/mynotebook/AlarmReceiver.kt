package com.example.mynotebook

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.mynotebook.domain.task.model.TaskModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {

    @Inject
    lateinit var taskNotificationService: TaskNotificationService

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent != null) {
            // Obtener los extras del intent
            val extras = intent.extras
            // Verificar si los extras no son nulos y contienen la tarea
            if (extras != null && extras.containsKey("EXTRA_TASK")) {
                // Obtener la tarea del intent
                val task = extras?.getSerializable("EXTRA_TASK") as? TaskModel
                // Verificar si la tarea no es nula
                if (task != null) {
                    // Mostrar la notificación de la tarea
                    TaskNotificationService(context).showNotification(task)
                }
            }
        }
    }
}