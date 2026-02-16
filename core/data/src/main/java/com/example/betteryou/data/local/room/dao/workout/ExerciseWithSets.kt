package com.example.betteryou.data.local.room.dao.workout

import androidx.room.Embedded
import androidx.room.Relation
import com.example.betteryou.data.local.room.entity.workout.ExerciseSetEntity
import com.example.betteryou.data.local.room.entity.workout.WorkoutExerciseEntity

data class ExerciseWithSets(
    @Embedded val exercise: WorkoutExerciseEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "workoutExerciseId"
    )
    val sets: List<ExerciseSetEntity>
)
