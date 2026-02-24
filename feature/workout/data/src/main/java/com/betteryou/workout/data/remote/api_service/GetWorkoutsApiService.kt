package com.betteryou.workout.data.remote.api_service

import com.betteryou.workout.data.remote.model.ExerciseDto
import retrofit2.Response
import retrofit2.http.GET

interface GetWorkoutsApiService {
    @GET(EXERCISES)
    suspend fun getWorkouts(): Response<List<ExerciseDto>>

    companion object {
        const val EXERCISES = "exercises"
    }
}