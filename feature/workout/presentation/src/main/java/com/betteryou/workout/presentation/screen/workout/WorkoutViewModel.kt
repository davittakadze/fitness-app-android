package com.betteryou.workout.presentation.screen.workout

import androidx.lifecycle.viewModelScope
import com.betteryou.workout.domain.usecase.workout.DeleteWorkoutUseCase
import com.betteryou.workout.domain.usecase.workout.GetAllSavedWorkoutsUseCase
import com.betteryou.workout.domain.usecase.workout.GetExercisesUseCase
import com.betteryou.workout.domain.usecase.workout.SaveWorkoutUseCase
import com.betteryou.workout.presentation.screen.workout.WorkoutSideEffect.*
import com.betteryou.workout.presentation.mapper.toDomain
import com.betteryou.workout.presentation.mapper.toPresentation
import com.betteryou.workout.presentation.model.ExerciseUI
import com.bettetyou.core.model.GetExercise
import com.example.betteryou.core_ui.R
import com.example.betteryou.domain.usecase.GetUserIdUseCase
import com.example.betteryou.presentation.common.BaseViewModel
import com.example.betteryou.presentation.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val getExercisesUseCase: GetExercisesUseCase,
    private val saveWorkoutUseCase: SaveWorkoutUseCase,
    private val getAllSavedWorkoutsUseCase: GetAllSavedWorkoutsUseCase,
    private val deleteWorkoutUseCase: DeleteWorkoutUseCase,
    private val getUserIdUseCase: GetUserIdUseCase
) : BaseViewModel<WorkoutState, WorkoutEvent, WorkoutSideEffect>(WorkoutState()) {

    override fun onEvent(event: WorkoutEvent) {
        when (event) {
            is WorkoutEvent.OnSearchChange -> {

                val filteredList = state.value.workouts.filter {
                    it.category.trim().lowercase()
                        .contains(event.exercise, ignoreCase = true) || it.name.trim()
                        .lowercase().contains(event.exercise, ignoreCase = true)
                }

                updateState { copy(searchQuery = event.exercise, filteredWorkouts = filteredList) }
            }

            is WorkoutEvent.OnExerciseNameChange -> updateState { copy(workoutName = event.name) }


            is WorkoutEvent.OnSelectExercise -> toggleExerciseCard(event)
            is WorkoutEvent.LoadWorkouts -> loadWorkouts()
            is WorkoutEvent.OnSaveWorkout -> saveWorkout(event.title, event.exercises)
            is WorkoutEvent.ShowSheet -> openSheet()
            is WorkoutEvent.DismissSheet -> dismissSheet()
            is WorkoutEvent.ObserveWorkouts -> observeWorkouts()
            is WorkoutEvent.NavigateToDetails -> {
                emitSideEffect(NavigateToDetails(event.workoutId))
            }

            is WorkoutEvent.DeleteWorkout -> {
                viewModelScope.launch {
                    deleteWorkoutUseCase.invoke(event.workoutId)
                }
            }
        }
    }

    private fun observeWorkouts() {
        val userId = getUserIdUseCase.invoke() ?: ""
        getAllSavedWorkoutsUseCase.invoke(userId)
            .onEach { workouts ->
                updateState { copy(myWorkouts = workouts) }
            }
            .launchIn(viewModelScope)
    }


    private fun loadWorkouts() {
        handleResponse(
            apiCall = { getExercisesUseCase.invoke() },
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
                    ShowError(
                        UiText.StringResource(
                            R.string.something_went_wrong
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

    private fun saveWorkout(title: String, exercises: List<ExerciseUI>) {
        if (title.isEmpty()) {
            emitSideEffect(
                ShowError(
                    UiText.StringResource(
                        R.string.enter_title
                    )
                )
            )
            return
        }
        if (exercises.isEmpty()) {
            emitSideEffect(
                ShowError(
                    UiText.StringResource(
                        R.string.add_at_least_one_exercise
                    )
                )
            )
            return
        }

        viewModelScope.launch {
            val userId = getUserIdUseCase.invoke() ?: ""
            saveWorkoutUseCase.invoke(title, exercises.map(ExerciseUI::toDomain), userId)

            dismissSheet()
        }
    }



    private fun dismissSheet() {
        val workouts = state.value.workouts.map { it.copy(isSelected = false) }
        updateState {
            copy(
                isSheetOpen = false,
                workoutName = "",
                searchQuery = "",
                workouts = workouts,
                filteredWorkouts = workouts
            )
        }
    }

    private fun openSheet() = updateState { copy(isSheetOpen = true) }

    private fun toggleExerciseCard(event: WorkoutEvent.OnSelectExercise) {
        val updatedWorkouts = state.value.workouts.map {
            if (it.id == event.workout.id) {
                it.copy(isSelected = !it.isSelected)
            } else {
                it
            }
        }

        val updatedFilteredWorkouts = updatedWorkouts.filter {
            val query = state.value.searchQuery

            if (query.isBlank()) true
             else {
                it.category.contains(query, ignoreCase = true) ||
                        it.name.contains(query, ignoreCase = true)
            }
        }

        updateState {
            copy(
                workouts = updatedWorkouts,
                filteredWorkouts = updatedFilteredWorkouts
            )
        }
    }

}