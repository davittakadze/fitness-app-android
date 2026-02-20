package com.betteryou.feature.explore.presentation.screen.details

sealed interface DetailsEvent {
    data class FetchDetails(val workoutId: String) : DetailsEvent
    data object NavigateBack : DetailsEvent
}