package com.example.betteryou.data.local.room.entity.product

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val nameEn: String,
    val nameKa: String,
    val photo: String,
    val calories: Double,
    val protein: Double,
    val carbs: Double,
    val fat: Double,
    val descriptionEn: String,
    val descriptionKa: String,
)