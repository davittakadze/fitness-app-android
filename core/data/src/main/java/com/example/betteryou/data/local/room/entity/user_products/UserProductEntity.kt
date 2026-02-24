package com.example.betteryou.data.local.room.entity.user_products

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_daily_products",
    indices = [
        Index(value = ["userId", "date"])
    ]
)
data class UserProductEntity(
    @PrimaryKey
    val id: Long,
    val userId: String?,
    val name: Map<String, String>,
    val photo: String,
    val calories: Double,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val description: Map<String, String>,
    val quantity: Double,
    val date: Long,
)