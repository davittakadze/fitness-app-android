package com.example.betteryou.data.local.room.entity.explore

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "explore_exercises")
data class ExerciseEntity(
    @PrimaryKey
    val id: String,
    val titleEn: String,
    val titleKa: String,
    val descriptionEn: String,
    val descriptionKa: String,
    val musclesTargetedEn: List<String>,
    val musclesTargetedKa: List<String>,
    val imageUrl: String
)
