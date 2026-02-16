package com.betteryou.workout.presentation.mapper

import com.betteryou.workout.presentation.model.ExerciseUI
import com.bettetyou.core.model.GetExercise

fun ExerciseUI.toDomain() = GetExercise (
    id = id,
    name = name,
    category = category,
    imageUrl = imageUrl
)

fun GetExercise.toPresentation() = ExerciseUI (
    id = id,
    name = name,
    category = category,
    imageUrl = imageUrl
)