package com.betteryou.feature.notification.data.repository

import com.betteryou.feature.notification.data.mapper.toDomain
import com.bettetyou.feature.notification.domain.model.GetNotification
import com.bettetyou.feature.notification.domain.repository.NotificationRepository
import com.example.betteryou.data.local.room.dao.notification.NotificationDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val notificationDao: NotificationDao
) : NotificationRepository {
    override fun getNotifications(): Flow<List<GetNotification>> {
        return notificationDao.getAllNotifications().map {
            it.map { notificationEntity ->
                notificationEntity.toDomain()
            }
        }
    }

    override suspend fun deleteNotificationById(id: Int) {
        notificationDao.deleteNotificationById(id)
    }

    override suspend fun updateReadStatus(id: Int, isRead: Boolean) {
        notificationDao.updateReadStatus(id, isRead)
    }
}