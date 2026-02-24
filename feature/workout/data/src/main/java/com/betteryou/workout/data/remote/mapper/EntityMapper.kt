package com.betteryou.workout.data.remote.mapper

import com.bettetyou.core.model.WorkoutHistory
import com.example.betteryou.data.local.room.entity.workout.WorkoutHistoryEntity

fun WorkoutHistory.toEntity() = WorkoutHistoryEntity(
    workoutTitle = workoutTitle,
    timestamp = timestamp,
    durationMillis = durationMillis,
    exercisesJson = exercisesJson,
    userId = userId
)

