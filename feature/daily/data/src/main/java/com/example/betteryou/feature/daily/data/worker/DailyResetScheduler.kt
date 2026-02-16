package com.example.betteryou.feature.daily.data.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DailyResetScheduler @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun schedule() {
        val request = PeriodicWorkRequestBuilder<DailyResetWorker>(
            1, TimeUnit.DAYS
        )
            .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "daily_reset_work",
                ExistingPeriodicWorkPolicy.UPDATE,
                request
            )
    }

    private fun calculateInitialDelay(): Long {
        val now = java.time.ZonedDateTime.now()
        val nextMidnight = now.toLocalDate()
            .plusDays(1)
            .atStartOfDay(now.zone)

        return java.time.Duration.between(now, nextMidnight).toMillis()
    }
}