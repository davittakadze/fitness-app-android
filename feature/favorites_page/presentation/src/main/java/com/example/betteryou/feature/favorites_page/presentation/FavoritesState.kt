package com.example.betteryou.feature.favorites_page.presentation

import com.example.betteryou.feature.favorites_page.presentation.model.FavoriteMealUi

data class FavoritesState (
    val isLoading:Boolean=false,
    val selectedMeal: FavoriteMealUi?=null,
    val favouriteMeals:List<FavoriteMealUi> = emptyList(),
)