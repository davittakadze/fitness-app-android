package com.example.betteryou.feature.recipes.presentation

import androidx.lifecycle.viewModelScope
import com.example.betteryou.domain.common.DatastoreKeys.USER_LANGUAGE_KEY
import com.example.betteryou.domain.usecase.GetPreferencesUseCase
import com.example.betteryou.domain.usecase.GetUserIdUseCase
import com.example.betteryou.feature.recipes.domain.usecase.AddFavoriteMealUseCase
import com.example.betteryou.feature.recipes.domain.usecase.GetFavoriteMealUseCase
import com.example.betteryou.feature.recipes.domain.usecase.GetMealUseCase
import com.example.betteryou.feature.recipes.domain.usecase.RemoveFavoriteMealUseCase
import com.example.betteryou.feature.recipes.presentation.mapper.toDomain
import com.example.betteryou.feature.recipes.presentation.mapper.toPresentation
import com.example.betteryou.presentation.common.BaseViewModel
import com.example.betteryou.presentation.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val mealUseCase: GetMealUseCase,
    private val favoriteMealUseCase: AddFavoriteMealUseCase,
    private val removeFavoriteMealUseCase: RemoveFavoriteMealUseCase,
    private val getFavoriteMealUseCase: GetFavoriteMealUseCase,
    private val getIdUseCase: GetUserIdUseCase,
    //datastore
    private val getPreferencesUseCase: GetPreferencesUseCase,
) : BaseViewModel<RecipesState, RecipesEvent, RecipesSideEffect>(RecipesState()) {

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
                            ).toDomain(),
                            currentLang = getPreferencesUseCase(
                                USER_LANGUAGE_KEY,
                                ""
                            ).first()
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
            apiCall = {
                mealUseCase.getMeals(
                    currentLang = getPreferencesUseCase(
                        USER_LANGUAGE_KEY,
                        ""
                    ).first()
                )
            },
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
            apiCall = {
                getFavoriteMealUseCase.invoke(
                    getIdUseCase.invoke()!!, currentLang = getPreferencesUseCase(
                        USER_LANGUAGE_KEY,
                        ""
                    ).first()
                )
            },
            onSuccess = { resource ->
                val currentMeals = state.value.meals

                val updatedFavorites = currentMeals.filter { meal ->
                    resource.any { fav ->
                        fav.id == meal.id
                    }
                }

                updateState {
                    copy(
                        isLoading = false,
                        favouriteMeals = updatedFavorites
                    )
                }
            },
            onError = { resource->
                updateState { copy(isLoading = false) }
                emitSideEffect(RecipesSideEffect.ShowError(UiText.DynamicString(resource)))
            },
            onLoading = {
                updateState { copy(isLoading = it.isLoading) }
            }
        )
    }
}