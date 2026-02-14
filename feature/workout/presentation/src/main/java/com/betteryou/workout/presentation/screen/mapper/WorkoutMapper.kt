package com.betteryou.workout.presentation.screen.mapper

import com.betteryou.workout.domain.model.GetExercise
import com.betteryou.workout.presentation.screen.model.ExerciseUI

fun GetExercise.toPresentation() = ExerciseUI (
    id = id,
    name = name,
    category = category,
    imageUrl = imageUrl
)