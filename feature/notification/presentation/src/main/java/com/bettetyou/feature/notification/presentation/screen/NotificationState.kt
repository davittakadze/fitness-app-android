package com.bettetyou.feature.notification.presentation.screen

import com.bettetyou.feature.notification.presentation.model.NotificationUi
import com.example.betteryou.core_ui.R

data class NotificationState(
    val selectedTabIndex: Int = 0,
    val tabs: List<Int> = listOf(R.string.reminders, R.string.history),
    val isWaterReminderEnabled: Boolean = false,
    val isNotificationPermissionGranted: Boolean = false,
    val showPermissionRationaleDialog: Boolean = false,
    val notifications: List<NotificationUi> = emptyList()
)