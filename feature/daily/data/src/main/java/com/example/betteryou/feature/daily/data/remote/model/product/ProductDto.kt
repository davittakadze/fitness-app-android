package com.example.betteryou.feature.daily.data.remote.model.product

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val name: Map<String, String>,
    val photo: String,
    val calories: Double,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val description: Map<String, String>
)