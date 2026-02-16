package com.betteryou.workout.domain.repository

interface ExerciseRepository {
    suspend fun addEmptySet(workoutExerciseId: String)
    suspend fun removeSet(setId: Long)
    suspend fun updateSetData(setId: Long, reps: String, weight: String)
}