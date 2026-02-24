package com.example.betteryou.feature.recipes.presentation

import com.example.betteryou.presentation.common.UiText

sealed interface RecipesSideEffect {
    data class ShowError(val error: UiText) : RecipesSideEffect
}