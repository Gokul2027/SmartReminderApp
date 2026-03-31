package com.smartreminder.app.location

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.smartreminder.app.worker.GeofenceSyncWorker

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (
            intent.action == Intent.ACTION_BOOT_COMPLETED ||
            intent.action == Intent.ACTION_MY_PACKAGE_REPLACED
        ) {
            GeofenceSyncWorker.enqueue(context)
        }
    }
}
