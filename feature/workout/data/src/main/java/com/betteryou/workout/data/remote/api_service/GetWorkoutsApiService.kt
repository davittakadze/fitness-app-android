package com.betteryou.workout.data.remote.api_service

import com.betteryou.workout.data.remote.model.ExerciseDto
import retrofit2.Response
import retrofit2.http.GET

interface GetWorkoutsApiService {
    @GET(WORKOUTS)
    suspend fun getWorkouts(): Response<List<ExerciseDto>>

    companion object {
        const val WORKOUTS = "3af647b8-3520-4049-aea6-4b345a6ca2a7"
    }
}