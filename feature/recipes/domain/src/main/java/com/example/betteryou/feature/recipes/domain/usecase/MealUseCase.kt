package com.example.betteryou.feature.recipes.domain.usecase

import com.example.betteryou.feature.recipes.domain.repository.RecipeRepository
import javax.inject.Inject

class MealUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
){
    suspend fun getMeals() = recipeRepository.getMeals()
}