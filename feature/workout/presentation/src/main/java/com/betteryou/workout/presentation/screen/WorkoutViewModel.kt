package com.betteryou.workout.presentation.screen

import com.betteryou.workout.domain.usecase.GetWorkoutsUseCase
import com.betteryou.workout.presentation.screen.mapper.toPresentation
import com.bettetyou.core.model.GetExercise
import com.example.betteryou.presentation.common.BaseViewModel
import com.example.betteryou.presentation.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val getWorkoutsUseCase: GetWorkoutsUseCase,
) : BaseViewModel<WorkoutState, WorkoutEvent, WorkoutSideEffect>(WorkoutState()) {

    override fun onEvent(event: WorkoutEvent) {
        when (event) {
            is WorkoutEvent.OnSearchChange -> {
                val filteredList = state.value.workouts.filter {
                    it.category.trim().lowercase().contains(event.exercise) || it.name.trim().lowercase().contains(event.exercise)
                }


                updateState { copy(searchQuery = event.exercise, filteredWorkouts = filteredList) }
            }

            is WorkoutEvent.OnExerciseNameChange -> {
                updateState { copy(workoutName = event.name) }
            }

            is WorkoutEvent.OnSelectExercise -> toggleExerciseCard(event)


            is WorkoutEvent.LoadWorkouts -> loadWorkouts()
        }
    }

    private fun toggleExerciseCard(event: WorkoutEvent.OnSelectExercise) {
        val currentList = state.value.filteredWorkouts
        val updatedList = currentList.map {
            if (it.id == event.workout.id)
                it.copy(isSelected = !it.isSelected)
            else it
        }
        updateState { copy(workouts = updatedList ,filteredWorkouts = updatedList) }
    }

    private fun loadWorkouts() {
        handleResponse(
            apiCall = { getWorkoutsUseCase.invoke() },
            onSuccess = { response ->
                val mappedModel = response.map(GetExercise::toPresentation)

                updateState {
                    copy(
                        workouts = mappedModel,
                        filteredWorkouts = mappedModel,
                        loader = false
                    )
                }
            },
            onError = { response ->
                emitSideEffect(
                    WorkoutSideEffect.ShowError(
                        UiText.StringResource(
                            com.example.betteryou.core_res.R.string.something_went_wrong
                        )
                    )
                )
                updateState { copy(loader = false) }
            },
            onLoading = { loader ->
                updateState { copy(loader = loader.isLoading) }
            }
        )
    }

}