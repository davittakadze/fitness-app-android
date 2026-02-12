package com.example.betteryou.feature.daily.data.remote.mapper

import com.example.betteryou.data.local.room.entity.UserNutritionEntity
import com.example.betteryou.feature.daily.data.remote.model.UserDto
import com.example.betteryou.feature.daily.domain.model.User

fun UserDto.toEntity(): UserNutritionEntity {
    return UserNutritionEntity(
        userId = userId,
        name = name,
        age = age,
        gender = gender,
        height = height,
        weight = weight,
        activityLevel = activityLevel,
        goal = goal,
        dailyCalories = dailyCalories,
        protein = protein,
        fats = fats,
        carbs = carbs,
        createdAt = createdAt
    )
}

fun UserNutritionEntity.toDomain(): User {
    return User(
        userId = userId,
        name = name,
        age = age,
        gender = gender,
        height = height,
        weight = weight,
        activityLevel = activityLevel,
        goal = goal,
        dailyCalories = dailyCalories,
        protein = protein,
        fats = fats,
        carbs = carbs,
        createdAt = createdAt
    )
}

