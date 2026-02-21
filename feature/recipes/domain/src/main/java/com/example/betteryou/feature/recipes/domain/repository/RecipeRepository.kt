package com.example.betteryou.feature.recipes.domain.repository

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.recipes.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getMeals(): Flow<Resource<List<Recipe>>>
    suspend fun getFavoriteMeals(userId:String?): Flow<Resource<List<Recipe>>>

    suspend fun addFavoriteMeal(meal: Recipe)

    suspend fun removeFavoriteMealById(mealId: Long, userId: String?)
}