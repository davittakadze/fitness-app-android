package com.example.betteryou.feature.recipes.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    val category: String,
    val title: String,
    val imageUrl: String,
    val ingredientCount: Int,
    val ingredients: List<String>,
    val cookingTime: String,
    val difficulty: String,
    val recipe: String
)