package com.betteryou.feature.history.presentation.screen.model

data class HistoryUI (
    val id: Long,
    val workoutTitle: String,
    val timestamp: Long,
    val durationMillis: Long,
    val exercises: List<ExerciseDetail>
)