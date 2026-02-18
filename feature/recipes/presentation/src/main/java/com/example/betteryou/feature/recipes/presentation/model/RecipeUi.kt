package com.example.betteryou.feature.recipes.presentation.model

data class RecipeUi (
    val id:Long,
    val category: String,
    val title: String,
    val imageUrl: String,
    val ingredientCount: Int,
    val ingredients: List<String>,
    val cookingTime: String,
    val difficulty: String,
    val recipe: String
)