package com.example.betteryou.data.local.room.entity.workout

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_exercises")
data class WorkoutExerciseEntity(
    @PrimaryKey val id: String,
    val workoutId: String,
    val exerciseId: String,
    val name: String,
    val category: String,
    val imageUrl: String
)