package com.example.betteryou.feature.favorites_page.domain.repository

import com.example.betteryou.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import com.example.betteryou.feature.favorites_page.domain.model.Recipe

interface FavoritesPageRepository {
    fun getFavoriteMeals(): Flow<Resource<List<Recipe>>>
    suspend fun removeFavoriteMealById(mealId: Long)
}