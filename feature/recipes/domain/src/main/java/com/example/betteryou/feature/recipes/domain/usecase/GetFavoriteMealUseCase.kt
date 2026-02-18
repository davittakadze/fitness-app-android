package com.example.betteryou.feature.recipes.domain.usecase

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.recipes.domain.model.Recipe
import com.example.betteryou.feature.recipes.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteMealUseCase @Inject constructor(
    private val favoriteMealRepository: RecipeRepository,
) {
    suspend fun invoke(): Flow<Resource<List<Recipe>>> = favoriteMealRepository.getFavoriteMeals()
}