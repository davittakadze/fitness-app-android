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
    val title: Map<String, String>,
    val imageUrl: String,
    val ingredientCount: Int,
    val ingredients: Map<String, List<String>>,
    val cookingTime: Map<String, String>,
    val difficulty: Map<String, String>,
    val recipe: Map<String, String>
)