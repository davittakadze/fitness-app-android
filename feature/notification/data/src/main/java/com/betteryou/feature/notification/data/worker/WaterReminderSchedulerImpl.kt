package com.betteryou.feature.notification.data.worker

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bettetyou.feature.notification.domain.repository.WaterReminderScheduler
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WaterReminderSchedulerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : WaterReminderScheduler {
//    override fun schedule() {
//        val workRequest = PeriodicWorkRequestBuilder<WaterReminderWorker>(
//            4, TimeUnit.HOURS
//        )
//            .setConstraints(
//                Constraints.Builder()
//                    .setRequiresBatteryNotLow(true)
//                    .build()
//            )
//            .build()
//
//        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
//            WORK_NAME,
//            ExistingPeriodicWorkPolicy.UPDATE,
//            workRequest
//        )
//    }

    // This is for testing purposes
    override fun schedule() {
        val workRequest = OneTimeWorkRequestBuilder<WaterReminderWorker>()
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "water_reminder_test",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    override fun cancel() {
        WorkManager.getInstance(context).cancelUniqueWork(WORK_NAME)
    }

    companion object {
        const val WORK_NAME = "water_reminder_work"
    }
}