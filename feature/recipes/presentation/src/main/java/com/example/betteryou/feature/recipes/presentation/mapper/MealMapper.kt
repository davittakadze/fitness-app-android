package com.example.betteryou.feature.recipes.presentation.mapper

import com.example.betteryou.feature.recipes.domain.model.Recipe
import com.example.betteryou.feature.recipes.presentation.model.RecipeUi

fun Recipe.toPresentation(): RecipeUi {
    return RecipeUi(
        id=id,
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

fun RecipeUi.toDomain(): Recipe {
    return Recipe(
        id=id,
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


