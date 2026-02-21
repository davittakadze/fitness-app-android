package com.example.betteryou.feature.favorites_page.presentation

import com.example.betteryou.feature.favorites_page.presentation.model.FavoriteMealUi

sealed interface FavoritesEvent {
    data class RemoveFavorite(val mealId: Long) : FavoritesEvent
    data class OnItemClick(val item: FavoriteMealUi): FavoritesEvent
    data class SelectMeal(val item: FavoriteMealUi): FavoritesEvent
    data object OnDismissSheet: FavoritesEvent
    data object OnBackClick: FavoritesEvent
}
