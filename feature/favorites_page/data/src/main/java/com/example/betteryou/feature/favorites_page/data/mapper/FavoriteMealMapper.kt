package com.example.betteryou.feature.favorites_page.data.mapper

import com.example.betteryou.data.local.room.entity.meal.FavoriteMealEntity
import com.example.betteryou.feature.favorites_page.domain.model.Recipe

fun FavoriteMealEntity.toDomain(currentLang: String = "en"): Recipe {
    return Recipe(
        id = id,
        userId = userId,
        category = category,
        title = title[currentLang] ?: title["en"].orEmpty(),
        imageUrl = imageUrl,
        ingredientCount = ingredientCount,
        ingredients = ingredients[currentLang] ?: ingredients["en"].orEmpty(),
        cookingTime = cookingTime[currentLang] ?: cookingTime["en"].orEmpty(),
        difficulty = difficulty[currentLang] ?: difficulty["en"].orEmpty(),
        recipe = recipe[currentLang] ?: recipe["en"].orEmpty()
    )
}