package com.bettetyou.feature.notification.presentation.screen

sealed interface NotificationEvent {
    data object OnBackClick : NotificationEvent
    data class OnWaterReminderToggled(val isChecked: Boolean) : NotificationEvent
    data class OnPermissionResultReceived(val isGranted: Boolean) : NotificationEvent
    data object ObserveWaterReminderStatus : NotificationEvent
    data object GetNotifications : NotificationEvent
    data class DeleteNotificationById(val id: Int) : NotificationEvent
    data class OnWaterNotificationClicked(val id: String) : NotificationEvent
}