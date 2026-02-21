package com.example.betteryou.feature.favorites_page.presentation

import com.example.betteryou.presentation.common.UiText

sealed interface FavoritesSideEffect {
    data class ShowError(val message: UiText) : FavoritesSideEffect
    data object NavigateBack : FavoritesSideEffect
}
