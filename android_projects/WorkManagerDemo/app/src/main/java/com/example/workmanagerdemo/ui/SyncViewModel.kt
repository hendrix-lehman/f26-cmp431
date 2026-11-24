package com.example.workmanagerdemo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkInfo
import com.example.workmanagerdemo.data.WorkScheduler
import kotlinx.coroutines.flow.Flow

class SyncViewModel(private val scheduler: WorkScheduler) : ViewModel() {

    val workInfo: Flow<List<WorkInfo>> = scheduler.syncWorkInfo

    fun triggerSync(requireCharging: Boolean = true) {
        scheduler.scheduleSync(requireCharging)
    }

    fun cancelSync() {
        scheduler.cancelSync()
    }
}

class SyncViewModelFactory(
    private val scheduler: WorkScheduler
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SyncViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SyncViewModel(scheduler) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
