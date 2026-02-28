package com.betteryou.feature.explore.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class ExerciseDto(
    val id: String,
    val title: LocalizationDto,
    val imageUrl: String,
    val description: LocalizationDto,
    val musclesTargeted: LocalizationListDto
)