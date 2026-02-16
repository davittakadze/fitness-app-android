package com.example.betteryou.data.local.room.entity.nutrition

import androidx.room.Entity
import androidx.room.PrimaryKey

//for feataure 'daily'
@Entity(tableName = "user_nutrition")
data class NutritionEntity(
    @PrimaryKey val userId: String,
    val dailyCalories: Double,
    val protein: Double,
    val fats: Double,
    val carbs: Double,
    val water: Double
)