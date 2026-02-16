package com.example.betteryou.data.mapper

import com.bettetyou.core.model.GetExercise
import com.example.betteryou.data.local.room.entity.workout.WorkoutExerciseEntity

fun WorkoutExerciseEntity.toDomain(): GetExercise {
    return GetExercise(
        id = this.exerciseId,
        name = this.name,
        category = this.category,
        imageUrl = this.imageUrl
    )
}