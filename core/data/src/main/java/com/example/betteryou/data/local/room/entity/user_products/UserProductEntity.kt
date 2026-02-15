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
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userId: String,
    val productId: Int,
    val name: String,
    val photo: String,
    val calories: Int,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val description: String,
    val quantity: Int,
    val date: Long,
)