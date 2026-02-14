package com.betteryou.workout.domain.repository

import com.bettetyou.core.model.GetExercise
import com.bettetyou.core.model.Workout
import com.example.betteryou.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface WorkoutsRepository {
    suspend fun getWorkoutsFromApi(): Flow<Resource<List<GetExercise>>>

    // 2. ახალი ვორქაუთის შენახვა ლოკალურ ბაზაში
    // აბრუნებს String-ს (გენერირებულ ID-ს), რომ ნავიგაციას გავატანოთ
    suspend fun saveWorkout(title: String, exercises: List<GetExercise>): String

    // 3. შენახული ვორქაუთების სია (Main Screen-ისთვის)
    fun getAllSavedWorkouts(): Flow<List<Workout>>
    fun getWorkoutById(id: String): Flow<Workout?>

    // 5. ვორქაუთის წაშლა (თუ იუზერს აღარ მოუნდება)
    suspend fun deleteWorkout(workoutId: String)
}