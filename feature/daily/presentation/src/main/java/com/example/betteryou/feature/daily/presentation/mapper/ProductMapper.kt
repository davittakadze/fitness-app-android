package com.example.betteryou.feature.daily.presentation.mapper

import com.example.betteryou.feature.daily.domain.model.Product
import com.example.betteryou.feature.daily.presentation.model.ProductUi

fun Product.toPresentation(): ProductUi {
    return ProductUi(
        id = id,
        name = name,
        photo = photo,
        calories = calories,
        protein = protein,
        carbs = carbs,
        fat = fat,
        description = description
    )
}