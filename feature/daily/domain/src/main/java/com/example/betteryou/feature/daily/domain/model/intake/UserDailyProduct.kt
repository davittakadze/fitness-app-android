package com.example.betteryou.feature.daily.domain.model.intake

data class UserDailyProduct(
    val id: Long,
    val userId: String?,
    val name: String,
    val photo: String,
    val calories: Double,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val description: String,
    val quantity: Double,
    val date: Long
)