package com.example.betteryou.feature.recipes.domain.model

data class Recipe(
    val id:Long,
    val userId:String?=null,
    val category: String,
    val title: String,
    val imageUrl: String,
    val ingredientCount: Int,
    val ingredients: List<String>,
    val cookingTime: String,
    val difficulty: String,
    val recipe: String
)