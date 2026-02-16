package com.betteryou.workout.presentation.mapper

import com.betteryou.workout.presentation.model.WorkoutUI
import com.bettetyou.core.model.Workout

fun Workout.toPresentation() = WorkoutUI(
    id = id,
    title = title,
    exercises = exercises.map { it.exercise.toPresentation() }
)