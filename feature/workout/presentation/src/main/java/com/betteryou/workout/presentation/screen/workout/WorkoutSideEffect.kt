package com.betteryou.workout.presentation.screen.workout

import com.example.betteryou.presentation.common.UiText

sealed interface WorkoutSideEffect {
    data class ShowError(val message: UiText) : WorkoutSideEffect
    data class NavigateToDetails(val workoutId: String) : WorkoutSideEffect
}