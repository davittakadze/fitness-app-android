package com.betteryou.feature.explore.presentation.model

data class ExerciseUI (
    val id: String,
    val title: String,
    val imageUrl: String,
    val description: String,
    val musclesTargeted: List<String>
)