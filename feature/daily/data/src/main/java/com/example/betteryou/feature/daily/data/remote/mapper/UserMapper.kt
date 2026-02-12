package com.example.betteryou.feature.daily.data.remote.mapper

import com.example.betteryou.data.local.room.entity.nutrition.NutritionEntity
import com.example.betteryou.feature.daily.data.remote.model.UserDto
import com.example.betteryou.feature.daily.domain.model.Nutrient

fun UserDto.toEntity(): NutritionEntity {
    return NutritionEntity(
        userId = userId,
        dailyCalories = dailyCalories,
        protein = protein,
        fats = fats,
        carbs = carbs,
        water = water
    )
}

fun NutritionEntity.toDomain(): Nutrient {
    return Nutrient(
        protein = protein,
        fats = fats,
        carbs = carbs,
        water = water,
        dailyCalories=dailyCalories
    )
}

