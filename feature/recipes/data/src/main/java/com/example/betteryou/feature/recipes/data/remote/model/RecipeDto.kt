package com.example.betteryou.feature.recipes.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipeDto(
    val id: Long,
    val category: String,
    val title: Map<String, String>,
    val imageUrl: String,
    val ingredientCount: Int,
    val ingredients: Map<String, List<String>>,
    val cookingTime: Map<String, String>,
    val difficulty: Map<String, String>,
    val recipe: Map<String, String>
)