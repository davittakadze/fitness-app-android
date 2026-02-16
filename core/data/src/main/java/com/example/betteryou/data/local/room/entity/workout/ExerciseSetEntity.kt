package com.example.betteryou.data.local.room.entity.workout

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise_sets",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutExerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExerciseSetEntity(
    @PrimaryKey(autoGenerate = true) val setId: Long = 0,
    val workoutExerciseId: String,
    val reps: String = "",
    val weight: String = "",
    val isCompleted: Boolean = false
)