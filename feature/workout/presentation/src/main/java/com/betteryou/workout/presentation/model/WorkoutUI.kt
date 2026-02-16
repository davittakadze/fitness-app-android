package com.betteryou.workout.presentation.model

data class WorkoutUI (
    val id: String,
    val title: String,
    val exercises: List<ExerciseUI>
)