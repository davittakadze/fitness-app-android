package com.betteryou.feature.explore.presentation.screen.details

import com.example.betteryou.presentation.common.UiText

sealed interface DetailsSideEffect {
    data class ShowError(val message: UiText) : DetailsSideEffect
    data object NavigateBack : DetailsSideEffect
}