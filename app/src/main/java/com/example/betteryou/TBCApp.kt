package com.example.betteryou

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.betteryou.feature.notification.data.worker.WaterReminderSchedulerImpl
import com.bettetyou.feature.notification.domain.repository.WaterReminderScheduler
import com.example.betteryou.feature.daily.data.worker.DailyResetScheduler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class TBCApp : Application(), Configuration.Provider {
    @Inject
    lateinit var dailyResetScheduler: dagger.Lazy<DailyResetScheduler>

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        dailyResetScheduler.get().schedule()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}