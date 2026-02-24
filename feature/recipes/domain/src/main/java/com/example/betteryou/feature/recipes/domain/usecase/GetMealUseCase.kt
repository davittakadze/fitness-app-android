package com.example.betteryou.feature.recipes.domain.usecase

import com.example.betteryou.feature.recipes.domain.repository.RecipeRepository
import javax.inject.Inject

class GetMealUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
){
    suspend fun getMeals(currentLang: String) = recipeRepository.getMeals(currentLang)
}