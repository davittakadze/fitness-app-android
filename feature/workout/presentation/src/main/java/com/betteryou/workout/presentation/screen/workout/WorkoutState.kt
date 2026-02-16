package com.betteryou.workout.presentation.screen.workout

import com.betteryou.workout.presentation.model.ExerciseUI
import com.bettetyou.core.model.Workout

data class WorkoutState (
    val tabs: List<String> = listOf("Create", "Plans"),
    val myWorkouts: List<Workout> = emptyList(),
    val isSheetOpen: Boolean = false,
    val workoutName: String = "",
    val searchQuery: String = "",
    val workouts: List<ExerciseUI> = emptyList(),
    val filteredWorkouts: List<ExerciseUI> = emptyList(),
    val loader: Boolean = false,
)