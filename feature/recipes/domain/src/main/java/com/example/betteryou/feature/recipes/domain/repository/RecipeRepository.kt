package com.example.betteryou.feature.recipes.domain.repository

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.recipes.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getMeals(currentLang: String): Flow<Resource<List<Recipe>>>
    suspend fun getFavoriteMeals(userId:String?, currentLang: String): Flow<Resource<List<Recipe>>>

    suspend fun addFavoriteMeal(meal: Recipe,currentLang: String)

    suspend fun removeFavoriteMealById(mealId: Long, userId: String?)
}