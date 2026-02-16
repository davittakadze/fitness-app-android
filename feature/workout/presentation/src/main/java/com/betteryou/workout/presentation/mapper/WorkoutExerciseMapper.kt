package com.betteryou.workout.presentation.mapper

import com.betteryou.workout.presentation.model.ExerciseSetUI
import com.betteryou.workout.presentation.model.WorkoutExerciseUI
import com.bettetyou.core.model.WorkoutExercise

fun WorkoutExercise.toPresentation() = WorkoutExerciseUI(
    id = this.id,
    exercise = this.exercise,
    sets = this.sets.map { domainSet ->
        ExerciseSetUI(
            setId = domainSet.id,
            reps = domainSet.reps,
            weight = domainSet.weight,
            isCompleted = domainSet.isCompleted
        )
    }
)