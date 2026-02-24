package com.example.betteryou.feature.daily.presentation.model

data class ProductUi(
    val id: Int,
    val name: String,
    val description: String,
    val photo: String,
    val calories: Double,
    val protein: Double,
    val carbs: Double,
    val fat: Double
)