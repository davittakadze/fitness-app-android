package com.betteryou.workout.presentation.screen.workout

import com.betteryou.workout.presentation.model.ExerciseUI

sealed interface WorkoutEvent {
    data class OnExerciseNameChange(val name: String) : WorkoutEvent
    data class OnSearchChange(val exercise: String) : WorkoutEvent
    data class OnSelectExercise(val workout: ExerciseUI) : WorkoutEvent
    data class OnSaveWorkout(val title: String, val exercises: List<ExerciseUI>) : WorkoutEvent
    data class NavigateToDetails(val workoutId: String) : WorkoutEvent
    data class DeleteWorkout(val workoutId: String) : WorkoutEvent
    data object ObserveWorkouts : WorkoutEvent
    data object ShowSheet : WorkoutEvent
    data object DismissSheet : WorkoutEvent
    data object LoadWorkouts : WorkoutEvent
}