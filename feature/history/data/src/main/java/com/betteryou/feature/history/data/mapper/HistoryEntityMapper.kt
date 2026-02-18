package com.betteryou.feature.history.data.mapper

import com.betteryou.feature.history.domain.model.GetHistory
import com.example.betteryou.data.local.room.entity.workout.WorkoutHistoryEntity

fun WorkoutHistoryEntity.toDomain() = GetHistory(
    id = id,
    workoutTitle = workoutTitle,
    timestamp = timestamp,
    durationMillis = durationMillis,
    exercises = exercisesJson
)