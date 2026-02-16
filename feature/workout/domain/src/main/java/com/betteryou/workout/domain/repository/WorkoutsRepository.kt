package com.betteryou.workout.domain.repository

import com.bettetyou.core.model.GetExercise
import com.bettetyou.core.model.Workout
import com.bettetyou.core.model.WorkoutHistory
import com.example.betteryou.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface WorkoutsRepository {
    suspend fun getWorkoutsFromApi(): Flow<Resource<List<GetExercise>>>

    suspend fun saveWorkout(title: String, exercises: List<GetExercise>): String

    fun getAllSavedWorkouts(): Flow<List<Workout>>
    fun getWorkoutById(id: String): Flow<Workout?>

    suspend fun deleteWorkout(workoutId: String)
}