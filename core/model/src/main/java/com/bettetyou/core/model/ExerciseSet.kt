package com.bettetyou.core.model

data class ExerciseSet(
    val id: Long,
    val reps: String,
    val weight: String,
    val isCompleted: Boolean
)