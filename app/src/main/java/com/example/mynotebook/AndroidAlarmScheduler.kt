package com.example.mynotebook

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import com.example.mynotebook.domain.task.model.TaskModel
import com.example.mynotebook.domain.task.utils.CalendarAlarm
import com.example.mynotebook.domain.task.utils.DailyAlarm
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Calendar
import javax.inject.Inject

class AndroidAlarmScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun schedule(item: TaskModel) {
        val alarmState = item.alarmState
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("EXTRA_TASK_ALARM", item.alarmState)
        }

        if (alarmState.isActive){
            alarmState.dailyAlarm?.let {dailyAlarm ->
                scheduleDailyAlarm(dailyAlarm)
            }
            alarmState.calendarAlarm?.let{calendarAlarm ->
                scheduleCalendarAlarm(calendarAlarm)
            }
        }
    }

    private fun scheduleCalendarAlarm(calendarAlarm: CalendarAlarm) {
        TODO("Not yet implemented")
    }

    private fun scheduleDailyAlarm(dailyAlarm: DailyAlarm) {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, dailyAlarm.hour)
            set(Calendar.MINUTE, dailyAlarm.hour)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    override fun cancel(item: TaskModel) {
        TODO("Not yet implemented")
    }
}