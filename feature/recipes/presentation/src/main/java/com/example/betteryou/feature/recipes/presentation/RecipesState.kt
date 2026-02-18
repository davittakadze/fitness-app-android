package com.example.betteryou.feature.recipes.presentation

import com.example.betteryou.feature.recipes.presentation.model.Meal
import com.example.betteryou.feature.recipes.presentation.model.RecipeUi

data class  RecipesState (
    val categoryList:List<Meal> = Meal.entries,
    val selectedCategory:Meal=Meal.ALL,
    val isLoading:Boolean=false,
    val meals:List<RecipeUi> = emptyList(),
    val selectedMeal:RecipeUi?=null,
    val favouriteMeals:List<RecipeUi> = emptyList(),
    val isSearching: Boolean = false,
    val searchQuery: String = ""
)