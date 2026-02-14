package com.betteryou.workout.presentation.screen

import com.betteryou.workout.presentation.screen.model.ExerciseUI

sealed interface WorkoutEvent {
    data class OnExerciseNameChange(val name: String) : WorkoutEvent
    data class OnSearchChange(val exercise: String) : WorkoutEvent
    data class OnSelectExercise(val workout: ExerciseUI) : WorkoutEvent
    data object LoadWorkouts : WorkoutEvent
}