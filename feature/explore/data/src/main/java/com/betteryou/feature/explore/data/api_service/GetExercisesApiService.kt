package com.betteryou.feature.explore.data.api_service

import com.betteryou.feature.explore.data.model.ExerciseDto
import retrofit2.Response
import retrofit2.http.GET

interface GetExercisesApiService {
    @GET("1c350973-a7ac-47d5-a702-308c2cdadce6")
    suspend fun getExercises(): Response<List<ExerciseDto>>
}