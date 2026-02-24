package com.betteryou.feature.notification.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.betteryou.core.notifications.NotificationHelper
import com.example.betteryou.core_ui.R
import com.example.betteryou.data.local.room.dao.notification.NotificationDao
import com.example.betteryou.data.local.room.entity.notification.NotificationEntity
import com.example.betteryou.domain.common.NotificationConfig
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class WaterReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val notificationHelper: NotificationHelper,
    private val config: NotificationConfig,
    private val notificationDao: NotificationDao,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val currentHour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)

            if (currentHour >= PM_23 || currentHour < AM_10) {
                return Result.success()
            }

            val title = applicationContext.getString(R.string.water_reminder_title)
            val body = applicationContext.getString(R.string.water_reminder_body)

            notificationDao.insertNotification(
                NotificationEntity(
                    title = title,
                    message = body,
                    type = WATER
                )
            )

            notificationHelper.showNotification(
                config = config,
                title = title,
                message = body
            )
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val PM_23 = 23
        const val AM_10 = 10
        const val WATER = "WATER"
    }
}