package com.example.betteryou.data.local.room.entity.nutrition

import androidx.room.Entity
import androidx.room.PrimaryKey

//for feataure 'daily'
@Entity(tableName = "user_nutrition")
data class NutritionEntity(
    @PrimaryKey val userId: String,
    val dailyCalories: Int,
    val protein: Int,
    val fats: Int,
    val carbs: Int,
    val water: Double
)