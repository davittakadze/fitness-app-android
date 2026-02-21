package com.example.betteryou.feature.favorites_page.data.mapper

import com.example.betteryou.data.local.room.entity.meal.FavoriteMealEntity
import com.example.betteryou.feature.favorites_page.domain.model.Recipe

fun FavoriteMealEntity.toDomain(): Recipe {
    return Recipe(
        id = id,
        category = category,
        title = title,
        imageUrl = imageUrl,
        ingredientCount = ingredientCount,
        ingredients = ingredients,
        cookingTime = cookingTime,
        difficulty = difficulty,
        recipe = recipe
    )
}