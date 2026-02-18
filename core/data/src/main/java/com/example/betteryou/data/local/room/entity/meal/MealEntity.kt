package com.example.betteryou.data.local.room.entity.meal

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "meals")
data class MealEntity (
    @PrimaryKey
    val id: Long,
    val category: String,
    val title: String,
    val imageUrl: String,
    val ingredientCount: Int,
    val ingredients: List<String>,
    val cookingTime: String,
    val difficulty: String,
    val recipe: String
)

class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun listToString(list: List<String>): String {
        return list.joinToString(",")
    }
}