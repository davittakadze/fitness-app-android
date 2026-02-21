package com.example.betteryou.feature.recipes.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.betteryou.domain.usecase.GetUserIdUseCase
import com.example.betteryou.feature.recipes.domain.usecase.AddFavoriteMealUseCase
import com.example.betteryou.feature.recipes.domain.usecase.GetFavoriteMealUseCase
import com.example.betteryou.feature.recipes.domain.usecase.GetMealUseCase
import com.example.betteryou.feature.recipes.domain.usecase.RemoveFavoriteMealUseCase
import com.example.betteryou.feature.recipes.presentation.mapper.toDomain
import com.example.betteryou.feature.recipes.presentation.mapper.toPresentation
import com.example.betteryou.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val mealUseCase: GetMealUseCase,
    private val favoriteMealUseCase: AddFavoriteMealUseCase,
    private val removeFavoriteMealUseCase: RemoveFavoriteMealUseCase,
    private val getFavoriteMealUseCase: GetFavoriteMealUseCase,
    private val getIdUseCase: GetUserIdUseCase,
) : BaseViewModel<RecipesState, RecipesEvent, Unit>(RecipesState()) {

    init {
        loadMeals()
    }

    override fun onEvent(event: RecipesEvent) {
        when (event) {
            is RecipesEvent.OnCategoryClick -> {
                updateState {
                    copy(selectedCategory = event.item)
                }
                Log.d("VM_DEBUG", "Selected category: ${event.item.displayName}")
            }

            is RecipesEvent.SelectMeal -> {
                updateState {
                    copy(selectedMeal = event.item)
                }
            }

            is RecipesEvent.OnFavouriteClick -> {
                viewModelScope.launch {
                    val isAlreadyFavorite =
                        state.value.favouriteMeals.any { it.id == event.item.id }

                    if (isAlreadyFavorite) {
                        removeFavoriteMealUseCase.removeFavoriteMealById(
                            event.item.id,
                            getIdUseCase.invoke()
                        )
                    } else {
                        favoriteMealUseCase.addFavoriteMeal(
                            event.item.copy(
                                userId = getIdUseCase.invoke()
                            ).toDomain()
                        )
                    }
                    updateState {
                        val newList = if (isAlreadyFavorite) {
                            favouriteMeals.filter { it.id != event.item.id }
                        } else {
                            favouriteMeals + event.item
                        }
                        copy(favouriteMeals = newList)
                    }
                }
            }

            RecipesEvent.OnDismissSheet -> updateState {
                copy(selectedMeal = null)
            }

            is RecipesEvent.OnItemClick -> updateState {
                copy(selectedMeal = event.item)
            }

            RecipesEvent.OnSearchClick -> {
                updateState {
                    copy(isSearching = true, searchQuery = "")
                }
            }

            is RecipesEvent.OnSearchQueryChange -> {
                updateState {
                    copy(searchQuery = event.query)
                }
            }

            RecipesEvent.OnSearchClose -> updateState {
                copy(isSearching = false, searchQuery = "")
            }
        }
    }

    private fun loadMeals() {
        handleResponse(
            apiCall = { mealUseCase.getMeals(getIdUseCase.invoke()) },
            onLoading = {
                updateState { copy(isLoading = it.isLoading) }
            },
            onSuccess = { resource ->
                updateState {
                    copy(meals = resource.map { it.toPresentation() })
                }
                loadFavoriteMeals()
            },
            onError = {
                updateState { copy(isLoading = false) }
            }
        )
    }

    private fun loadFavoriteMeals() {
        handleResponse(
            apiCall = { getFavoriteMealUseCase.invoke(getIdUseCase.invoke()!!) },
            onSuccess = { resource ->
                val currentMeals = state.value.meals

                val updatedFavorites = currentMeals.filter { meal ->
                    resource.any { fav ->
                        fav.id == meal.id
                    }
                }

                updateState {
                    copy(favouriteMeals = updatedFavorites)
                }
            },
            onError = {

            },
            onLoading = {

            }
        )
    }
}