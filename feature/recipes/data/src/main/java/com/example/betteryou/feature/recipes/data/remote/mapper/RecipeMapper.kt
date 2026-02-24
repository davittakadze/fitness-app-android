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

fun MealEntity.toDomain(currentLang: String): Recipe {
    return Recipe(
        id = id,
        category = category,
        title = title[currentLang] ?: title["en"] ?: "",
        imageUrl = imageUrl,
        ingredientCount = ingredientCount,
        ingredients = ingredients[currentLang] ?: ingredients["en"] ?: emptyList(),
        cookingTime = cookingTime[currentLang] ?: cookingTime["en"] ?: "",
        difficulty = difficulty[currentLang] ?: difficulty["en"] ?: "",
        recipe = recipe[currentLang] ?: recipe["en"] ?: ""
    )
}

fun FavoriteMealEntity.toDomain(currentLang: String = "en"): Recipe {
    return Recipe(
        id = id,
        category = category,
        title = title[currentLang] ?: title["en"] ?: "",
        imageUrl = imageUrl,
        ingredientCount = ingredientCount,
        ingredients = ingredients[currentLang] ?: ingredients["en"] ?: emptyList(),
        cookingTime = cookingTime[currentLang] ?: cookingTime["en"] ?: "",
        difficulty = difficulty[currentLang] ?: difficulty["en"] ?: "",
        recipe = recipe[currentLang] ?: recipe["en"] ?: ""
    )
}

fun Recipe.toEntity(currentLang: String): FavoriteMealEntity {
    return FavoriteMealEntity(
        id = id,
        userId = userId!!,
        category = category,
        title = mapOf(currentLang to title),
        imageUrl = imageUrl,
        ingredientCount = ingredientCount,
        ingredients = mapOf(currentLang to ingredients),
        cookingTime = mapOf(currentLang to cookingTime),
        difficulty = mapOf(currentLang to difficulty),
        recipe = mapOf(currentLang to recipe)
    )
}