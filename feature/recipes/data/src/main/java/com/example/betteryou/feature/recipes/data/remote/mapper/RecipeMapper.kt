package com.example.betteryou.feature.recipes.data.remote.mapper

import com.example.betteryou.data.local.room.entity.meal.FavoriteMealEntity
import com.example.betteryou.data.local.room.entity.meal.MealEntity
import com.example.betteryou.feature.recipes.data.remote.model.RecipeDto
import com.example.betteryou.feature.recipes.domain.model.Recipe

fun RecipeDto.toEntity(): MealEntity {
    return MealEntity(
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

fun MealEntity.toDomain(): Recipe {
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

fun FavoriteMealEntity.toDomain(): Recipe {
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

fun Recipe.toEntity(): FavoriteMealEntity {
    return FavoriteMealEntity(
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