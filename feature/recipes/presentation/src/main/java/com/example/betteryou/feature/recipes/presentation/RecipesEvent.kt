package com.example.betteryou.feature.recipes.presentation

import com.example.betteryou.feature.recipes.presentation.model.Meal
import com.example.betteryou.feature.recipes.presentation.model.RecipeUi

sealed interface RecipesEvent {
    data class OnCategoryClick(val item: Meal): RecipesEvent
    data class SelectMeal(val item: RecipeUi): RecipesEvent

    data class OnFavouriteClick(val item: RecipeUi): RecipesEvent
}