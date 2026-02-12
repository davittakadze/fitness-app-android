package com.example.betteryou.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


//for feataure 'daily'
@Entity(tableName = "user_nutrition")
data class UserNutritionEntity(
    @PrimaryKey val userId: String,
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
    val createdAt: Long,
)