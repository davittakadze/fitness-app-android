package com.example.betteryou.feature.recipes.data.remote.service

import com.example.betteryou.feature.recipes.data.remote.model.RecipeDto
import retrofit2.Response
import retrofit2.http.GET

interface RecipeService {
    @GET("9936325a-2618-4598-8870-56883585a79c ")
    suspend fun getMeals(): Response<List<RecipeDto>>
}