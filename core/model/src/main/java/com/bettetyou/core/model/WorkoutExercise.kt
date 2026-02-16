package com.bettetyou.core.model

data class WorkoutExercise(
    val id: String,
    val exercise: GetExercise,
    val sets: List<ExerciseSet>
)