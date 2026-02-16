package com.betteryou.workout.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseSetUI(
    val setId: Long,
    val reps: String,
    val weight: String,
    val isCompleted: Boolean = false
)