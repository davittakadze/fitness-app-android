package com.example.betteryou.feature.daily.presentation.mapper

import com.example.betteryou.feature.daily.domain.model.UserDailyProduct
import com.example.betteryou.feature.daily.presentation.model.UserDailyProductUi

fun UserDailyProduct.toPresentation(): UserDailyProductUi {
    return UserDailyProductUi(
        id=id,
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
}

fun UserDailyProductUi.toDomain(): UserDailyProduct{
    return UserDailyProduct(
        id =id,
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
}