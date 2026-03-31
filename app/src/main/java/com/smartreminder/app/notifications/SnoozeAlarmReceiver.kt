package com.smartreminder.app.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.smartreminder.app.R

class SnoozeAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationHelper.ensureChannel(context)

        val taskId = intent.getIntExtra("task_id", -1).takeIf { it != -1 } ?: return
        val title = intent.getStringExtra("notification_title").orEmpty()
        val body = intent.getStringExtra("notification_body").orEmpty()

        val notification = NotificationCompat.Builder(context, NotificationHelper.channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        try {
            NotificationManagerCompat.from(context).notify(taskId, notification)
        } catch (_: SecurityException) {
            // Notification permission may be denied.
        }
    }
}
