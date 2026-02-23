package com.example.betteryou.feature.daily.data.remote.mapper

import com.example.betteryou.data.local.room.entity.user_products.UserProductEntity
import com.example.betteryou.feature.daily.domain.model.intake.UserDailyProduct

fun UserDailyProduct.toEntity() = UserProductEntity(
    userId = userId,
    id = id,
    name = name,
    photo = photo,
    calories = calories,
    protein = protein,
    carbs = carbs,
    fat = fat,
    description = description,
    quantity = quantity,
    date = date
)

fun UserProductEntity.toDomain() = UserDailyProduct(
    id = id,
    userId = userId,
    name = name,
    photo = photo,
    calories = calories,
    protein = protein,
    carbs = carbs,
    fat = fat,
    description = description,
    quantity = quantity,
    date = date
)