package com.example.betteryou.feature.recipes.data.remote.service

import com.example.betteryou.feature.recipes.data.remote.model.RecipeDto
import retrofit2.Response
import retrofit2.http.GET

interface RecipeService {
    @GET("meals")
    suspend fun getMeals(): Response<List<RecipeDto>>
}