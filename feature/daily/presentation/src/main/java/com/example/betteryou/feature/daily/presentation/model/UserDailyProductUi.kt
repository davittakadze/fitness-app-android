package com.example.betteryou.feature.daily.presentation.model

data class UserDailyProductUi (
    val userId: String?=null,
    val productId: Int,
    val name: String,
    val photo: String,
    val calories: Int,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val description: String,
    val quantity: Int,
    val date: Long
)