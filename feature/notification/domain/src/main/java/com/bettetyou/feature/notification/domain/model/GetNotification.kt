package com.bettetyou.feature.notification.domain.model

data class GetNotification (
    val id: String,
    val title: String,
    val body: String,
    val type: String,
    val date: Long,
    val isRead: Boolean
)