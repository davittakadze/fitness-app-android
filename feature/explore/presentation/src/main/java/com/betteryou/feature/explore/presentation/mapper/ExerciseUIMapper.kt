package com.betteryou.feature.explore.presentation.mapper

import com.betteryou.feature.explore.domain.model.GetExercise
import com.betteryou.feature.explore.presentation.model.ExerciseUI

fun GetExercise.toPresentation() = ExerciseUI(
    id = id,
    title = title,
    imageUrl = imageUrl,
    description = description,
    musclesTargeted = musclesTargeted
)