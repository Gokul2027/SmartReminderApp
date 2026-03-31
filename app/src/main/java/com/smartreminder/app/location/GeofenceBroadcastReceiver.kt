package com.smartreminder.app.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.smartreminder.app.ai.HabitTracker
import com.smartreminder.app.data.db.AppDatabase
import com.smartreminder.app.notifications.NotificationHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult = goAsync()

        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
            try {
                val event = GeofencingEvent.fromIntent(intent)
                if (event == null) return@launch

                if (event.hasError()) {
                    Log.e(
                        "GeofenceReceiver",
                        GeofenceStatusCodes.getStatusCodeString(event.errorCode)
                    )
                    return@launch
                }

                val transitionLabel = when (event.geofenceTransition) {
                    Geofence.GEOFENCE_TRANSITION_ENTER -> "Enter"
                    Geofence.GEOFENCE_TRANSITION_EXIT -> "Exit"
                    else -> null
                } ?: return@launch

                val database = AppDatabase.getDatabase(context)
                val notificationHelper = NotificationHelper(context)
                val habitTracker = HabitTracker(context)

                event.triggeringGeofences.orEmpty().forEach { triggeredGeofence ->
                    val taskId = triggeredGeofence.requestId.toIntOrNull() ?: return@forEach
                    val task = database.taskDao().getTaskById(taskId) ?: return@forEach
                    if (task.isCompleted) return@forEach
                    if (!notificationHelper.shouldNotify(taskId, transitionLabel)) return@forEach

                    val contextHint = habitTracker.getPreferredTimeWindow(task.category)
                        ?.takeIf { habitTracker.isCurrentTimeWindow(it) }
                        ?.let { "This matches your usual ${it.lowercase()} window for ${task.category.lowercase()} tasks." }

                    notificationHelper.showTaskNotification(
                        task = task,
                        transitionLabel = transitionLabel,
                        contextHint = contextHint
                    )
                }
            } finally {
                pendingResult.finish()
            }
        }
    }
}
