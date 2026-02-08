package com.example.betteryou.feature.settings.presentation

import com.example.betteryou.presentation.common.UiText

sealed interface SettingSideEffects {
    data class ShowError(val message: UiText) : SettingSideEffects
    data object NavigateToProfile : SettingSideEffects
}