package com.betteryou.feature.explore.presentation.screen.explore

sealed interface ExploreEvent {
    object FetchExercises : ExploreEvent
    data class OnExerciseClick(val workoutId: String) : ExploreEvent
    data class OnSearchFieldChange(val input: String) : ExploreEvent
}