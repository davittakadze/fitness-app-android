package com.betteryou.workout.presentation.model

import com.bettetyou.core.model.GetExercise
import kotlinx.serialization.Serializable

@Serializable
data class WorkoutExerciseUI(
    val id: String,
    val exercise: GetExercise,
    val sets: List<ExerciseSetUI>
)