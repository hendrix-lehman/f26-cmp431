package com.example.workmanagerdemo.ui

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.work.WorkInfo
import com.example.workmanagerdemo.worker.SyncWorker

@Composable
fun SyncScreen(
    viewModel: SyncViewModel,
    modifier: Modifier = Modifier
) {
    val workInfoList by viewModel.workInfo.collectAsState(initial = emptyList())
    val currentWorkInfo = workInfoList.firstOrNull()

    // Toggle to test constraints easily on an emulator
    var requireCharging by remember { mutableStateOf(true) }

    // Request POST_NOTIFICATIONS permission on Android 13+
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { /* Permission handled */ }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "WorkManager Background Sync",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Map WorkInfo.State to readable status messages
        val statusText = when (currentWorkInfo?.state) {
            WorkInfo.State.ENQUEUED -> "Enqueued (Waiting for constraints...)"
            WorkInfo.State.RUNNING -> "Running Sync..."
            WorkInfo.State.SUCCEEDED -> "Sync Succeeded"
            WorkInfo.State.FAILED -> "Sync Failed"
            WorkInfo.State.BLOCKED -> "Sync Blocked"
            WorkInfo.State.CANCELLED -> "Sync Cancelled"
            else -> "Not Started"
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Status: $statusText",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )

                // Read Output Data returned via Result.success(outputData)
                if (currentWorkInfo?.state == WorkInfo.State.SUCCEEDED) {
                    val syncedCount = currentWorkInfo.outputData.getInt(SyncWorker.KEY_RECORDS_SYNCED, 0)
                    val timestamp = currentWorkInfo.outputData.getString(SyncWorker.KEY_SYNC_TIMESTAMP) ?: ""
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Synced $syncedCount items at $timestamp",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Constraint Toggle Switch
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Require Device Charging")
            Switch(
                checked = requireCharging,
                onCheckedChange = { requireCharging = it }
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Trigger Work Button
        Button(
            onClick = { viewModel.triggerSync(requireCharging = requireCharging) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Background Sync")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Cancel Work Button (Active only while Enqueued or Running)
        val isWorkActive = currentWorkInfo?.state == WorkInfo.State.ENQUEUED ||
                currentWorkInfo?.state == WorkInfo.State.RUNNING

        OutlinedButton(
            onClick = { viewModel.cancelSync() },
            enabled = isWorkActive,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancel Sync")
        }
    }
}