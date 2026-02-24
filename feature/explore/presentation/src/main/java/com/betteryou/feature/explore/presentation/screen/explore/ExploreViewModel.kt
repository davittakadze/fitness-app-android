package com.betteryou.feature.explore.presentation.screen.explore

import com.betteryou.feature.explore.domain.model.GetExercise
import com.betteryou.feature.explore.domain.usecase.GetExercisesUseCase
import com.betteryou.feature.explore.presentation.mapper.toPresentation
import com.example.betteryou.presentation.common.BaseViewModel
import com.example.betteryou.presentation.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.example.betteryou.core_ui.R
import kotlin.text.trim

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesUseCase,
) : BaseViewModel<ExploreState, ExploreEvent, ExploreSideEffect>(ExploreState()) {

    override fun onEvent(event: ExploreEvent) {
        when(event) {
            is ExploreEvent.OnExerciseClick -> {
                emitSideEffect(ExploreSideEffect.NavigateToDetails(event.workoutId))
            }


            is ExploreEvent.OnSearchFieldChange -> {
                val filteredList = state.value.exercises.filter {
                    it.title.trim().lowercase().contains(event.input, ignoreCase = true)
                }

                updateState { copy(searchInput = event.input, filteredExercises = filteredList) }
            }
        }
    }

    init {
        fetchExercises()
    }

    private fun fetchExercises() {
        handleResponse(
            apiCall = { getExercisesUseCase.invoke() },
            onSuccess = { resource ->
                val mappedValue = resource.map(GetExercise::toPresentation)

                updateState {
                    copy(
                        exercises = mappedValue,
                        filteredExercises = mappedValue,
                        isLoading = false
                    )
                }
            },
            onError = {
                updateState { copy(isLoading = false) }
                emitSideEffect(
                    ExploreSideEffect.ShowError(
                        UiText.StringResource(R.string.something_went_wrong)
                    )
                )
            },
            onLoading = { loader ->
                updateState { copy(isLoading = loader.isLoading) }
            }
        )
    }
}