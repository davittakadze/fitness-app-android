package com.example.betteryou.feature.daily.data.remote.model

data class IntakeDto (
    val dailyCalories:Int,
    val protein: Int,
    val fats: Int,
    val carbs: Int,
    val water: Double
)