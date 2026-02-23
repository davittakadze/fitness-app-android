package com.betteryou.feature.notification.data.mapper

import com.bettetyou.feature.notification.domain.model.GetNotification
import com.example.betteryou.data.local.room.entity.notification.NotificationEntity

fun NotificationEntity.toDomain() = GetNotification(
    id = id.toString(),
    title = title,
    body = message,
    type = type,
    date = timestamp,
    isRead = isRead
)