package com.betteryou.feature.explore.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseDto (
    val id: String,
    val title: String,
    val imageUrl: String,
    val description: String,
    val musclesTargeted: List<String>
)