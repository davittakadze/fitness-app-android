package com.example.betteryou.data.local.room.entity.intake

import androidx.room.Entity

@Entity(
    tableName = "daily_intake",
    primaryKeys = ["userId", "date"]
)
data class DailyIntakeEntity(
    val userId: String,
    val date: Long,
    val consumedCalories: Int,
    val protein: Int,
    val fat: Int,
    val carbs: Int,
    val water:Double
)