package com.betteryou.workout.data.remote.api_service

import com.betteryou.workout.data.remote.model.ExerciseDto
import retrofit2.Response
import retrofit2.http.GET

interface GetWorkoutsApiService {
    @GET("5116e291-fca5-4c56-ab25-feba12749e5a")
    suspend fun getWorkouts(): Response<List<ExerciseDto>>
}