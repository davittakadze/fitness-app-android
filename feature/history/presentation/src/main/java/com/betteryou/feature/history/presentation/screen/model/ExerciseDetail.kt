package com.betteryou.feature.history.presentation.screen.model

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseDetail(
    val id: String,
    val exercise: ExerciseInfo,
    val sets: List<WorkoutSet>
)
