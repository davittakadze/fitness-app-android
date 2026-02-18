package com.betteryou.feature.history.presentation.screen

import com.example.betteryou.presentation.common.UiText

sealed interface HistorySideEffect {
    data class ShowError(val message: UiText) : HistorySideEffect
    data object NavigateBack : HistorySideEffect
}