package com.betteryou.workout.presentation.model

data class ExerciseUI (
    val id: String,
    val name: String,
    val category: String,
    val imageUrl: String,
    val isSelected: Boolean = false
)