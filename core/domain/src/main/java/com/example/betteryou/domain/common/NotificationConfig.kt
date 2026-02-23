package com.example.betteryou.domain.common

interface NotificationConfig {
    val channelId: String
    val channelName: String
    val smallIconResId: Int
    val deepLinkUri: String
}