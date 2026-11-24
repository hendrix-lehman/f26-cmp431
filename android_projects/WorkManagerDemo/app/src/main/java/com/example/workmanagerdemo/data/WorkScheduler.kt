package com.example.workmanagerdemo.data

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.workmanagerdemo.worker.SyncWorker
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.TimeUnit

class WorkScheduler(context: Context) {

    private val workManager = WorkManager.getInstance(context.applicationContext)

    companion object {
        const val UNIQUE_SYNC_WORK_NAME = "SyncUniqueWork"
        const val SYNC_TAG = "tag_cloud_sync"
    }

    // Observe work state reactively by converting LiveData to Kotlin Flow
    val syncWorkInfo: Flow<List<WorkInfo>> = workManager
        .getWorkInfosForUniqueWorkLiveData(UNIQUE_SYNC_WORK_NAME)
        .asFlow()

    fun scheduleSync(requireCharging: Boolean = true) {
        // 1. Define Hardware & Network Constraints
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(requireCharging)
            .setRequiresBatteryNotLow(true)
            .build()

        // 2. Attach Input Data (< 10KB)
        val inputData = workDataOf(
            SyncWorker.KEY_SYNC_TAG to "LehmanCloudServer"
        )

        // 3. Build OneTimeWorkRequest with Exponential Backoff
        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .setInputData(inputData)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                15L,
                TimeUnit.SECONDS
            )
            .addTag(SYNC_TAG)
            .build()

        // 4. Enqueue Unique Work to avoid duplicate parallel tasks
        workManager.enqueueUniqueWork(
            UNIQUE_SYNC_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            syncRequest
        )
    }

    // Cancel enqueued or running work by its unique name
    fun cancelSync() {
        workManager.cancelUniqueWork(UNIQUE_SYNC_WORK_NAME)
    }
}