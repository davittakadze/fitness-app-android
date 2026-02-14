package com.example.betteryou.data.local.room.entity.workout

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "workout_exercises",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["workoutId"])]
)
data class WorkoutExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val workoutId: String,
    val exerciseId: String, // api id
    val name: String,
    val imageUrl: String,
    val category: String,
    val sets: Int = 3,
    val reps: Int = 12
)