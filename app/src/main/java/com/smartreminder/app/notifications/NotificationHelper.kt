package com.smartreminder.app.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.smartreminder.app.R
import com.smartreminder.app.data.model.Task
import com.smartreminder.app.ui.MainActivity

class NotificationHelper(private val context: Context) {

    private val prefs = context.getSharedPreferences("notification_gate", Context.MODE_PRIVATE)

    companion object {
        const val channelId = "smart_location_reminders"
        private const val channelName = "Location reminders"
        private const val duplicateWindowMs = 15 * 60_000L

        fun ensureChannel(context: Context) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

            val manager = context.getSystemService(NotificationManager::class.java)
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Alerts when you enter or leave saved reminder locations."
                enableLights(true)
                enableVibration(true)
            }
            manager.createNotificationChannel(channel)
        }
    }

    fun shouldNotify(taskId: Int, transitionLabel: String): Boolean {
        val key = "last_${taskId}_${transitionLabel.lowercase()}"
        val now = System.currentTimeMillis()
        val lastShown = prefs.getLong(key, 0L)
        if (now - lastShown < duplicateWindowMs) return false

        prefs.edit().putLong(key, now).apply()
        return true
    }

    fun showTaskNotification(
        task: Task,
        transitionLabel: String,
        contextHint: String? = null
    ) {
        ensureChannel(context)

        val title = "$transitionLabel reminder: ${task.name}"
        val body = buildString {
            append(
                if (task.locationName.isNotBlank()) {
                    "Location: ${task.locationName}."
                } else {
                    "You are near the saved reminder location."
                }
            )
            if (!contextHint.isNullOrBlank()) {
                append(" ")
                append(contextHint)
            }
        }

        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        val openIntent = PendingIntent.getActivity(
            context,
            task.id,
            Intent(context, MainActivity::class.java).apply {
                putExtra("task_id", task.id)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
            flags
        )

        val snoozeIntent = PendingIntent.getBroadcast(
            context,
            task.id + 10_000,
            Intent(context, SnoozeReceiver::class.java).apply {
                putExtra("task_id", task.id)
                putExtra("notification_title", title)
                putExtra("notification_body", body)
            },
            flags
        )

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setAutoCancel(true)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(openIntent)
            .setVibrate(longArrayOf(0, 250, 150, 250))
            .addAction(
                R.drawable.ic_snooze,
                context.getString(R.string.snooze_10_minutes),
                snoozeIntent
            )
            .build()

        try {
            NotificationManagerCompat.from(context).notify(task.id, notification)
        } catch (_: SecurityException) {
            // The user may have denied POST_NOTIFICATIONS on Android 13+.
        }
    }
}
