package com.example.betteryou.feature.daily.data.remote.mapper

import com.example.betteryou.feature.daily.data.remote.model.product.ProductDto
import com.example.betteryou.feature.daily.domain.model.Product

fun ProductDto.toDomain(): Product{
    return Product(
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