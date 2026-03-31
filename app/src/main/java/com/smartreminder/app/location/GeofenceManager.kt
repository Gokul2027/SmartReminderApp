package com.smartreminder.app.location

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.smartreminder.app.data.model.Task
import kotlinx.coroutines.tasks.await

class GeofenceManager(private val context: Context) {

    private val geofencingClient: GeofencingClient =
        LocationServices.getGeofencingClient(context)

    private val geofencePendingIntent: PendingIntent by lazy {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        PendingIntent.getBroadcast(context, 0, intent, flags)
    }

    @SuppressLint("MissingPermission")
    suspend fun addOrUpdateGeofence(task: Task): Result<Unit> = runCatching {
        removeGeofence(task.id)
        val geofence = buildGeofence(task)
        val request = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        geofencingClient.addGeofences(request, geofencePendingIntent).await()
    }

    suspend fun removeGeofence(taskId: Int): Result<Unit> = runCatching {
        geofencingClient.removeGeofences(listOf(taskId.toString())).await()
    }

    @SuppressLint("MissingPermission")
    suspend fun syncAll(tasks: List<Task>): Result<Unit> = runCatching {
        runCatching { geofencingClient.removeGeofences(geofencePendingIntent).await() }

        val activeTasks = tasks.filterNot { it.isCompleted }
        if (activeTasks.isEmpty()) return@runCatching

        val geofences = activeTasks.map(::buildGeofence)
        val request = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofences(geofences)
            .build()

        geofencingClient.addGeofences(request, geofencePendingIntent).await()
    }

    private fun buildGeofence(task: Task): Geofence {
        return Geofence.Builder()
            .setRequestId(task.id.toString())
            .setCircularRegion(task.latitude, task.longitude, task.radius)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setLoiteringDelay(30_000)
            .setTransitionTypes(
                when (task.reminderType.uppercase()) {
                    "EXIT" -> Geofence.GEOFENCE_TRANSITION_EXIT
                    else -> Geofence.GEOFENCE_TRANSITION_ENTER
                }
            )
            .build()
    }
}
