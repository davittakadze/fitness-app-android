package com.example.betteryou.data.local.room.entity.explore

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "explore_exercises")
data class ExerciseEntity (
    @PrimaryKey
    val id: String,
    val title: String,
    val imageUrl: String,
    val description: String,
    val musclesTargeted: List<String>
)