package com.betteryou.feature.explore.domain.model

data class GetExercise (
    val id: String,
    val title: String,
    val imageUrl: String,
    val description: String,
    val musclesTargeted: List<String>
)