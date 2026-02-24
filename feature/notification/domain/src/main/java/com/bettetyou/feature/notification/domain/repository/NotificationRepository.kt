package com.bettetyou.feature.notification.domain.repository

import com.bettetyou.feature.notification.domain.model.GetNotification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotifications(): Flow<List<GetNotification>>
    suspend fun deleteNotificationById(id: Int)
    suspend fun updateReadStatus(id: Int, isRead: Boolean)
}