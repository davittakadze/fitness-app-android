package com.example.betteryou.feature.daily.domain.model

data class User(
    val userId: String,
    val name: String,
    val age: Int,
    val gender: String,
    val height: Int,
    val weight: Int,
    val activityLevel: String,
    val goal: String,
    val dailyCalories: Int,
    val protein: Int,
    val fats: Int,
    val carbs: Int,
    val createdAt: Long
)