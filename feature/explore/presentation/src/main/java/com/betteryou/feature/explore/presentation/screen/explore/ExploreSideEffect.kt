package com.betteryou.feature.explore.presentation.screen.explore

import com.example.betteryou.presentation.common.UiText

sealed interface ExploreSideEffect {
    data class ShowError(val message: UiText) : ExploreSideEffect
    data class NavigateToDetails(val workoutId: String) : ExploreSideEffect
}