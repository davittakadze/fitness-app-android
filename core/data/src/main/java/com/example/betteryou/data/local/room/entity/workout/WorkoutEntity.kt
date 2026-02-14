package com.example.betteryou.data.local.room.entity.workout

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val createdAt: Long = System.currentTimeMillis()
)

