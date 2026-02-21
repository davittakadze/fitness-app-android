package com.example.betteryou.feature.recipes.domain.usecase

import com.example.betteryou.feature.recipes.domain.repository.RecipeRepository
import javax.inject.Inject

class RemoveFavoriteMealUseCase @Inject constructor(
    private val favoriteMealRepository: RecipeRepository,
) {
    suspend fun removeFavoriteMealById(mealId: Long, userId: String?) {
        favoriteMealRepository.removeFavoriteMealById(mealId, userId)
    }
}