package com.example.betteryou.feature.favorites_page.presentation.model

data class FavoriteMealUi (
    val id:Long,
    val userId:String,
    val category: String,
    val title: String,
    val imageUrl: String,
    val ingredientCount: Int,
    val ingredients: List<String>,
    val cookingTime: String,
    val difficulty: String,
    val recipe: String
)