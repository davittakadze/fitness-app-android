package com.example.betteryou.feature.daily.data.remote.model.user

data class UserDto(
    val userId: String = "",
    val name: String = "",
    val age: Int = 0,
    val gender: String = "",
    val height: Int = 0,
    val weight: Int = 0,
    val activityLevel: String = "",
    val goal: String = "",
    val dailyCalories: Int = 0,
    val protein: Int = 0,
    val fats: Int = 0,
    val carbs: Int = 0,
    val water: Double = 0.0
)