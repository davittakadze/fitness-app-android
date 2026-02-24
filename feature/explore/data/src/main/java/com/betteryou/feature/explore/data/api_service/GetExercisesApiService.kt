package com.betteryou.feature.explore.data.api_service

import com.betteryou.feature.explore.data.model.ExerciseDto
import retrofit2.Response
import retrofit2.http.GET

interface GetExercisesApiService {
    @GET(EXPLORE)
    suspend fun getExercises(): Response<List<ExerciseDto>>

    companion object {
        const val EXPLORE = "91b67b12-0dfa-4b40-98d4-e649e51bc05a"
    }
}