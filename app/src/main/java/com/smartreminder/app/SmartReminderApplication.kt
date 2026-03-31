package com.smartreminder.app

import android.app.Application
import androidx.preference.PreferenceManager
import com.smartreminder.app.notifications.NotificationHelper
import com.smartreminder.app.worker.GeofenceSyncWorker
import org.osmdroid.config.Configuration

class SmartReminderApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Configuration.getInstance().load(
            this,
            PreferenceManager.getDefaultSharedPreferences(this)
        )
        Configuration.getInstance().userAgentValue =
            "${BuildConfig.APPLICATION_ID}/${BuildConfig.VERSION_NAME}"

        NotificationHelper.ensureChannel(this)
        GeofenceSyncWorker.enqueue(this)
    }
}
