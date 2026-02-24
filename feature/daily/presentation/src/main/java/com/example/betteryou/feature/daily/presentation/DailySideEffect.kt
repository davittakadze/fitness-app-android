package com.example.betteryou.feature.daily.presentation

import com.example.betteryou.presentation.common.UiText

sealed interface DailySideEffect {
    data class ShowError(val error: UiText): DailySideEffect
    data object NavigateToNotifications: DailySideEffect
}