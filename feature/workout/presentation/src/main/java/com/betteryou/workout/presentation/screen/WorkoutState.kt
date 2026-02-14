package com.betteryou.workout.presentation.screen

import com.betteryou.workout.presentation.screen.model.ExerciseUI

data class WorkoutState (
    val tabs: List<String> = listOf("Create", "Plans"),
    val workoutName: String = "",
    val searchQuery: String = "",
    val workouts: List<ExerciseUI> = emptyList(),
    val filteredWorkouts: List<ExerciseUI> = emptyList(),
    val loader: Boolean = false,
)