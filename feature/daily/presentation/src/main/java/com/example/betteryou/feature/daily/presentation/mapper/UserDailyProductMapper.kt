package com.example.betteryou.feature.daily.presentation.mapper

import com.example.betteryou.feature.daily.domain.model.UserDailyProduct
import com.example.betteryou.feature.daily.presentation.model.UserDailyProductUi

fun UserDailyProduct.toPresentation(): UserDailyProductUi {
    return UserDailyProductUi(
        userId = userId,
        productId = productId,
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
        userId = userId,
        productId = productId,
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