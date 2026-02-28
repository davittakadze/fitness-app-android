package com.betteryou.feature.explore.data.model

import kotlinx.serialization.Serializable

@Serializable
internal data class LocalizationListDto(
    val en: List<String>,
    val ka: List<String>
)
