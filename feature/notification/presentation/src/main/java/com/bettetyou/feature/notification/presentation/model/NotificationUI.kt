package com.bettetyou.feature.notification.presentation.model

import com.bettetyou.feature.notification.domain.model.NotificationType

data class NotificationUi(
    val id: String,
    val title: String,
    val body: String,
    val type: NotificationType,
    val date: String,
    val isRead: Boolean
)