package com.bettetyou.feature.notification.presentation.mapper

import com.bettetyou.feature.notification.domain.model.GetNotification
import com.bettetyou.feature.notification.domain.model.NotificationType
import com.bettetyou.feature.notification.presentation.model.NotificationUi
import com.example.betteryou.util.formatToDateString

fun GetNotification.toPresentation() = NotificationUi(
    id = id,
    title = title,
    body = body,
    type = when(type.lowercase()) {
        "water" -> NotificationType.WATER
        else -> NotificationType.UNKNOWN
    },
    date = date.formatToDateString(),
    isRead = isRead
)