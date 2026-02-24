package com.bettetyou.core.model

data class WorkoutHistory(
    val id: Int? = null,
    val userId: String,
    val workoutTitle: String,
    val timestamp: Long,
    val durationMillis: Long,
    val exercisesJson: String
)