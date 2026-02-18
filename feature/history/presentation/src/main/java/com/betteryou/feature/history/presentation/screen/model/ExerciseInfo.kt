package com.betteryou.feature.history.presentation.screen.model

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseInfo(
    val id: String,
    val name: String,
    val category: String,
    val imageUrl: String
)