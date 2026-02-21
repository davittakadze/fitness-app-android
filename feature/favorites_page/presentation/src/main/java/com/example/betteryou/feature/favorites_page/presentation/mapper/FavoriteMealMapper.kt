package com.example.betteryou.feature.favorites_page.presentation.mapper

import com.example.betteryou.feature.favorites_page.domain.model.Recipe
import com.example.betteryou.feature.favorites_page.presentation.model.FavoriteMealUi

fun Recipe.toPresentation(): FavoriteMealUi {
    return FavoriteMealUi(
        userId = userId,
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