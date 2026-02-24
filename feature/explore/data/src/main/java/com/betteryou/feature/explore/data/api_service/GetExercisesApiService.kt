package com.betteryou.feature.explore.data.api_service

import com.betteryou.feature.explore.data.model.ExerciseDto
import retrofit2.Response
import retrofit2.http.GET

interface GetExercisesApiService {
    @GET(EXPLORE)
    suspend fun getExercises(): Response<List<ExerciseDto>>

    companion object {
        const val EXPLORE = "explore"
    }
}