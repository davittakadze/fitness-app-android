package com.betteryou.feature.explore.presentation.screen.details

import com.betteryou.feature.explore.presentation.model.ExerciseUI

data class DetailsState (
    val exercise: ExerciseUI? = null,
    val isLoading: Boolean = false
)