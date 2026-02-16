package com.betteryou.workout.presentation.screen.details

import com.betteryou.workout.presentation.model.WorkoutExerciseUI

data class DetailsState(
    val workoutTitle: String = "",
    val exercises: List<WorkoutExerciseUI> = emptyList(),
    val isLoading: Boolean = false,
)