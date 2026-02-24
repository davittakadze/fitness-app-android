package com.example.betteryou.feature.recipes.domain.usecase

import com.example.betteryou.feature.recipes.domain.model.Recipe
import com.example.betteryou.feature.recipes.domain.repository.RecipeRepository
import javax.inject.Inject

class AddFavoriteMealUseCase @Inject constructor(
    private val favoriteMealRepository: RecipeRepository,
) {
    suspend fun addFavoriteMeal(meal: Recipe, currentLang: String) {
        favoriteMealRepository.addFavoriteMeal(meal, currentLang)
    }
}