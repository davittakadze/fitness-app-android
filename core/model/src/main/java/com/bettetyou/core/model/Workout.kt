package com.bettetyou.core.model

data class Workout(
    val id: String,
    val title: String,
    val exercises: List<WorkoutExercise>
)