package com.example.betteryou.feature.recipes.presentation

import com.example.betteryou.feature.recipes.domain.usecase.MealUseCase
import com.example.betteryou.feature.recipes.presentation.mapper.toPresentation
import com.example.betteryou.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val mealUseCase: MealUseCase,
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
            }

            is RecipesEvent.SelectMeal -> {
                updateState {
                    copy(selectedMeal = event.item)
                }
            }

            is RecipesEvent.OnFavouriteClick -> {
                val currentFavourites=state.value.favouriteMeals
                updateState {
                    copy(
                        favouriteMeals = if (currentFavourites.contains(event.item)) {
                            currentFavourites - event.item
                        } else {
                            currentFavourites + event.item
                        }
                    )
                }
            }
        }
    }

    private fun loadMeals() {
        handleResponse(
            apiCall = { mealUseCase.getMeals() },
            onLoading = {
                updateState {
                    copy(isLoading = it.isLoading)
                }
            },
            onSuccess = { it ->
                updateState {
                    copy(meals = it.map { it.toPresentation() }, isLoading = false)
                }
            },
            onError = {
                updateState {
                    copy(isLoading = false)
                }
            }
        )
    }
}