package com.example.betteryou.data.local.room.entity.meal

import androidx.room.Entity

@Entity(
    tableName = "favorite_meals",
    primaryKeys = ["userId", "id"]
)
data class FavoriteMealEntity(
    val id: Long,
    val userId: String,
    val category: String,
    val title: String,
    val imageUrl: String,
    val ingredientCount: Int,
    val ingredients: List<String>,
    val cookingTime: String,
    val difficulty: String,
    val recipe: String,
)