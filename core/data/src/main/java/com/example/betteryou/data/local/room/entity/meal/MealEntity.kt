package com.example.betteryou.data.local.room.entity.meal

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey
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

class Converters {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    // Map<String, String>
    @TypeConverter
    fun fromStringMap(value: Map<String, String>): String =
        json.encodeToString(value)

    @TypeConverter
    fun toStringMap(value: String): Map<String, String> =
        json.decodeFromString(value)

    // Map<String, List<String>>
    @TypeConverter
    fun fromStringListMap(value: Map<String, List<String>>): String =
        json.encodeToString(value)

    @TypeConverter
    fun toStringListMap(value: String): Map<String, List<String>> =
        json.decodeFromString(value)

    // List<String>
    @TypeConverter
    fun fromStringList(value: List<String>): String =
        json.encodeToString(value)

    @TypeConverter
    fun toStringList(value: String): List<String> =
        json.decodeFromString(value)
}