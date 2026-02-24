package com.bettetyou.feature.notification.presentation.screen

import com.example.betteryou.presentation.common.UiText

sealed interface NotificationSideEffect {
    data object NavigateBack : NotificationSideEffect
    data object RequestNotificationPermission : NotificationSideEffect
    data object NavigateToDaily : NotificationSideEffect
    data class ShowPermissionRequiredMessage(val message: UiText) : NotificationSideEffect
}