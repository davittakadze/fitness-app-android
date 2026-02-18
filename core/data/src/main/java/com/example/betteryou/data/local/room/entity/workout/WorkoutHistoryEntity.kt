package com.example.betteryou.data.local.room.entity.workout

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_history")
data class WorkoutHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val workoutTitle: String,
    val timestamp: Long,
    val durationMillis: Long,
    val exercisesJson: String
)