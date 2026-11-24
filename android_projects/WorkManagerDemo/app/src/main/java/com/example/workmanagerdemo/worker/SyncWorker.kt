package com.example.workmanagerdemo.worker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val KEY_SYNC_TAG = "KEY_SYNC_TAG"
        const val KEY_SYNC_TIMESTAMP = "KEY_SYNC_TIMESTAMP"
        const val KEY_RECORDS_SYNCED = "KEY_RECORDS_SYNCED"
        const val CHANNEL_ID = "sync_notifications"
        const val NOTIFICATION_ID = 1001
    }

    // Switch to Dispatchers.IO for network/disk I/O operations
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        // 1. Read Input Data (capped at 10KB)
        val syncSource = inputData.getString(KEY_SYNC_TAG) ?: "DefaultCloud"

        try {
            // Simulate a 3-step cloud database sync
            for (step in 1..3) {
                delay(1000L) // Cooperative suspension point
            }

            // Prepare Output Data
            val timestamp = SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(Date())
            val outputData = workDataOf(
                KEY_SYNC_TIMESTAMP to timestamp,
                KEY_RECORDS_SYNCED to 42
            )

            // Post system notification on completion
            showSyncNotification(
                title = "Background Sync Complete",
                message = "Synced 42 records from $syncSource at $timestamp."
            )

            // 2. Return Result.success() with output payload
            Result.success(outputData)

        } catch (e: CancellationException) {
            // 3. Never suppress CancellationException; rethrow so WorkManager cancels cleanly
            throw e
        } catch (e: Exception) {
            // 4. Retry transient errors up to 3 attempts; otherwise fail permanently
            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }

    private fun showSyncNotification(title: String, message: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Sync Status",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for background data syncs"
            }
            val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.stat_notify_sync)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(applicationContext).notify(NOTIFICATION_ID, notification)
    }
}