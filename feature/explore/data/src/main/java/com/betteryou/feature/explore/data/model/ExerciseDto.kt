package com.betteryou.feature.explore.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseDto(
    val id: String,
    val title: LocalizationDto,
    val imageUrl: String,
    val description: LocalizationDto,
    val musclesTargeted: LocalizationListDto
)

@Serializable
data class LocalizationDto(
    val en: String,
    val ka: String
)

@Serializable
data class LocalizationListDto(
    val en: List<String>,
    val ka: List<String>
)
