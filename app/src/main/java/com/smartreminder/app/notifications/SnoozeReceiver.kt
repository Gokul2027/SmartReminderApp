package com.smartreminder.app.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build

class SnoozeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val taskId = intent.getIntExtra("task_id", -1).takeIf { it != -1 } ?: return
        val title = intent.getStringExtra("notification_title").orEmpty()
        val body = intent.getStringExtra("notification_body").orEmpty()

        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        val alarmIntent = PendingIntent.getBroadcast(
            context,
            taskId + 20_000,
            Intent(context, SnoozeAlarmReceiver::class.java).apply {
                putExtra("task_id", taskId)
                putExtra("notification_title", title)
                putExtra("notification_body", body)
            },
            flags
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val triggerAtMillis = System.currentTimeMillis() + 10 * 60_000L
        alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, alarmIntent)
    }
}
