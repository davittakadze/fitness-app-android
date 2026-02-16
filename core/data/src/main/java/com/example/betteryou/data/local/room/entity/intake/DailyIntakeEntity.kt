package com.example.betteryou.data.local.room.entity.intake

import androidx.room.Entity

@Entity(
    tableName = "daily_intake",
    primaryKeys = ["userId", "date"]
)
data class DailyIntakeEntity(
    val userId: String,
    val date: Long,
    val consumedCalories: Double,
    val protein: Double,
    val fat: Double,
    val carbs: Double,
    val water:Double
)