package com.betteryou.feature.history.presentation.screen.model

import kotlinx.serialization.Serializable

@Serializable
data class WorkoutSet(
    val setId: Int,
    val reps: String,
    val weight: String
)