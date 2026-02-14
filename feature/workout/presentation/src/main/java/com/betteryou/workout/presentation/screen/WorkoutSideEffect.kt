package com.betteryou.workout.presentation.screen

import com.example.betteryou.presentation.common.UiText

sealed interface WorkoutSideEffect {
    data class ShowError(val message: UiText) : WorkoutSideEffect
}