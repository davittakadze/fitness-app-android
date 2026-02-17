package com.example.betteryou.feature.recipes.data.remote.mapper

import com.example.betteryou.data.local.room.entity.meal.MealEntity
import com.example.betteryou.feature.recipes.data.remote.model.RecipeDto
import com.example.betteryou.feature.recipes.domain.model.Recipe

fun RecipeDto.toEntity(): MealEntity {
    return MealEntity(
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

fun MealEntity.toDomain(): Recipe {
    return Recipe(
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