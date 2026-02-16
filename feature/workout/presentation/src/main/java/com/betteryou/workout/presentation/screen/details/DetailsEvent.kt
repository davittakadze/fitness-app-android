package com.betteryou.workout.presentation.screen.details

sealed interface DetailsEvent {
    data class LoadWorkoutExercises(val workoutId: String) : DetailsEvent
    data class AddSet(val workoutExerciseId: String) : DetailsEvent
    data class RemoveSet(val setId: Long) : DetailsEvent
    data object SaveInHistory : DetailsEvent
    data class UpdateSet(val setId: Long, val reps: String, val weight: String) : DetailsEvent
}