package com.betteryou.feature.explore.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class LocalizationDto(
    val en: String,
    val ka: String
)