package com.smartreminder.app.worker

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.smartreminder.app.data.db.AppDatabase
import com.smartreminder.app.data.repository.TaskRepository
import com.smartreminder.app.location.GeofenceManager

class GeofenceSyncWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val hasFineLocation = ContextCompat.checkSelfPermission(
            applicationContext,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasFineLocation) return Result.success()

        val repository = TaskRepository(AppDatabase.getDatabase(applicationContext).taskDao())
        val activeTasks = repository.getActiveTaskSnapshot()

        return GeofenceManager(applicationContext).syncAll(activeTasks).fold(
            onSuccess = { Result.success() },
            onFailure = { Result.retry() }
        )
    }

    companion object {
        private const val uniqueWorkName = "geofence_sync"

        fun enqueue(context: Context) {
            val request = OneTimeWorkRequestBuilder<GeofenceSyncWorker>()
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .build()

            WorkManager.getInstance(context)
                .enqueueUniqueWork(uniqueWorkName, ExistingWorkPolicy.REPLACE, request)
        }
    }
}
