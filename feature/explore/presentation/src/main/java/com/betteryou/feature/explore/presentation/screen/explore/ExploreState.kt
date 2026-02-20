package com.betteryou.feature.explore.presentation.screen.explore

import com.betteryou.feature.explore.presentation.model.ExerciseUI

data class ExploreState (
    val exercises: List<ExerciseUI> = emptyList(),
    val filteredExercises: List<ExerciseUI> = emptyList(),
    val searchInput: String = "",
    val isLoading: Boolean = false
)