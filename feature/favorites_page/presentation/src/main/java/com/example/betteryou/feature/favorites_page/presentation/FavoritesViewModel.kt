package com.example.betteryou.feature.favorites_page.presentation

import androidx.lifecycle.viewModelScope
import com.example.betteryou.feature.favorites_page.domain.usecase.FavoriteMealUseCase
import com.example.betteryou.feature.favorites_page.domain.usecase.RemoveFavoriteMealUseCase
import com.example.betteryou.feature.favorites_page.presentation.mapper.toPresentation
import com.example.betteryou.presentation.common.BaseViewModel
import com.example.betteryou.presentation.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteMealUseCase: FavoriteMealUseCase,
    private val removeFavoriteMealUseCase: RemoveFavoriteMealUseCase,
) : BaseViewModel<FavoritesState, FavoritesEvent, FavoritesSideEffect>(FavoritesState()) {

    init {
        loadFavoriteMeals()
    }

    override fun onEvent(event: FavoritesEvent) {
        when (event) {
            is FavoritesEvent.OnDismissSheet -> updateState {
                copy(selectedMeal = null)
            }

            is FavoritesEvent.OnItemClick -> updateState {
                copy(selectedMeal = event.item)
            }

            is FavoritesEvent.RemoveFavorite -> {
                viewModelScope.launch {
                    removeFavoriteMealUseCase.invoke(event.mealId)
                    updateState {
                        copy(favouriteMeals = favouriteMeals.filter { it.id != event.mealId })
                    }
                }
            }

            is FavoritesEvent.SelectMeal -> updateState {
                copy(selectedMeal = event.item)
            }

            FavoritesEvent.OnBackClick -> {
                viewModelScope.launch {
                    emitSideEffect(FavoritesSideEffect.NavigateBack)
                }
            }
        }
    }

    private fun loadFavoriteMeals() {
        handleResponse(
            apiCall = { getFavoriteMealUseCase.invoke() },
            onSuccess = { resource ->
                updateState {
                    copy(isLoading = false, favouriteMeals = resource.map {
                        it.toPresentation()
                    })
                }
            },
            onError = { resource ->
                updateState { copy(isLoading = false) }
                emitSideEffect(
                    FavoritesSideEffect.ShowError(
                        UiText.DynamicString(resource)
                    )
                )
            },
            onLoading = {
                updateState { copy(isLoading = it.isLoading) }
            }
        )
    }
}