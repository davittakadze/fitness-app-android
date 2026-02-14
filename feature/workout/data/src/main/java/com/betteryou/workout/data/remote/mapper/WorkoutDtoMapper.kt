package com.betteryou.workout.data.remote.mapper

import com.betteryou.workout.data.remote.model.ExerciseDto
import com.bettetyou.core.model.GetExercise

fun ExerciseDto.toDomain() : GetExercise = GetExercise (
    id = id,
    name = name,
    category = category,
    imageUrl = imageUrl
)