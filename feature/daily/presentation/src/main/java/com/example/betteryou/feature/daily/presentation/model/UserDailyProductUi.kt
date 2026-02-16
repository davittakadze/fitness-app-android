package com.example.betteryou.feature.daily.presentation.model

data class UserDailyProductUi (
    val id: Long=System.currentTimeMillis(),
    val userId: String?=null,
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