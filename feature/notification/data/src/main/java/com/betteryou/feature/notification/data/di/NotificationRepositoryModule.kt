package com.betteryou.feature.notification.data.di

import com.betteryou.feature.notification.data.repository.NotificationRepositoryImpl
import com.bettetyou.feature.notification.domain.repository.NotificationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationRepositoryModule {
    @Binds
    abstract fun bindNotificationRepository(
        notificationRepositoryImpl: NotificationRepositoryImpl
    ): NotificationRepository
}