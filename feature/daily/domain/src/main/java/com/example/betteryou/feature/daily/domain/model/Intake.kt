package com.example.betteryou.feature.daily.domain.model

data class Intake(
    val dailyCalories:Int,
    val protein: Int,
    val fats: Int,
    val carbs: Int,
    val water: Double
)