package com.betteryou.feature.explore.presentation.screen.details

import com.betteryou.feature.explore.domain.usecase.GetDetailsUseCase
import com.betteryou.feature.explore.presentation.mapper.toPresentation
import com.betteryou.feature.explore.presentation.screen.explore.ExploreSideEffect
import com.example.betteryou.core_ui.R
import com.example.betteryou.presentation.common.BaseViewModel
import com.example.betteryou.presentation.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getDetailsUseCase: GetDetailsUseCase,
) : BaseViewModel<DetailsState,DetailsEvent, DetailsSideEffect> (DetailsState()) {

    override fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.FetchDetails -> fetchExercise(event.workoutId)
            DetailsEvent.NavigateBack -> emitSideEffect(DetailsSideEffect.NavigateBack)
        }
    }


    private fun fetchExercise(workoutId: String) {
        handleResponse(
            apiCall = {
                getDetailsUseCase.invoke(workoutId)
            },
            onSuccess = { resource ->
                updateState { copy(exercise = resource.toPresentation(), isLoading = false) }
            },
            onError = {
                updateState { copy(isLoading = false) }
                emitSideEffect(
                    DetailsSideEffect.ShowError(
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