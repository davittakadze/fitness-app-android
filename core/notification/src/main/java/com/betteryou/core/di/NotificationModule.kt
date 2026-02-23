package com.betteryou.core.di

import com.betteryou.core.notifications.WaterNotificationConfig
import com.example.betteryou.domain.common.NotificationConfig
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule {

    @Binds
    @Singleton
    abstract fun bindWaterNotificationConfig(
        impl: WaterNotificationConfig
    ): NotificationConfig
}