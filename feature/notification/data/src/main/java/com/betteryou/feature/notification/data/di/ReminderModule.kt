package com.betteryou.feature.notification.data.di

import com.betteryou.feature.notification.data.worker.WaterReminderSchedulerImpl
import com.bettetyou.feature.notification.domain.repository.WaterReminderScheduler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ReminderModule {
    @Binds
    abstract fun bindWaterReminderManager(
        scheduler: WaterReminderSchedulerImpl
    ): WaterReminderScheduler
}