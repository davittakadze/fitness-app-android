package com.betteryou.workout.presentation.screen.details

import com.example.betteryou.presentation.common.UiText

sealed class DetailsSideEffect {
    object NavigateBack : DetailsSideEffect()
    data class ShowError(val message: UiText) : DetailsSideEffect()
}