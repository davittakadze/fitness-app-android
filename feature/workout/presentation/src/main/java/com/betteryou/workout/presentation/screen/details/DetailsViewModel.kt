package com.betteryou.workout.presentation.screen.details

import androidx.lifecycle.viewModelScope
import com.betteryou.workout.domain.usecase.workout.GetWorkoutDetailsUseCase
import com.betteryou.workout.domain.usecase.history.SaveWorkoutHistoryUseCase
import com.betteryou.workout.domain.usecase.set.AddSetUseCase
import com.betteryou.workout.domain.usecase.set.DeleteSetUseCase
import com.betteryou.workout.domain.usecase.set.UpdateSetUseCase
import com.betteryou.workout.presentation.mapper.toPresentation
import com.bettetyou.core.model.WorkoutHistory
import com.example.betteryou.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getWorkoutDetailsUseCase: GetWorkoutDetailsUseCase,
    private val addSetUseCase: AddSetUseCase,
    private val deleteSetUseCase: DeleteSetUseCase,
    private val updateSetUseCase: UpdateSetUseCase,
    private val saveWorkoutHistoryUseCase: SaveWorkoutHistoryUseCase
) : BaseViewModel<DetailsState, DetailsEvent, DetailsSideEffect>(DetailsState()) {
    private val startTime = System.currentTimeMillis()

    override fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.LoadWorkoutExercises -> getWorkoutExercises(event.workoutId)
            is DetailsEvent.AddSet -> addSet(event.workoutExerciseId)
            is DetailsEvent.RemoveSet -> deleteSet(event.setId)
            is DetailsEvent.UpdateSet -> updateSet(event.setId, event.reps, event.weight)
            DetailsEvent.SaveInHistory -> finishWorkout()
        }
    }

    private fun addSet(exerciseId: String) {
        viewModelScope.launch {
            addSetUseCase(exerciseId)
        }
    }

    private fun deleteSet(setId: Long) {
        viewModelScope.launch {
            deleteSetUseCase(setId)
        }
    }

    private var updateJob: Job? = null

    private fun updateSet(setId: Long, reps: String, weight: String) {
        updateJob?.cancel()

        updateJob = viewModelScope.launch {
            delay(400L)
            updateSetUseCase(setId, reps, weight)
        }
    }

    private fun getWorkoutExercises(workoutId: String) {
        getWorkoutDetailsUseCase.invoke(workoutId).onEach { domainWorkout ->
            updateState {
                copy(
                    workoutTitle = domainWorkout?.title ?: "",
                    exercises = domainWorkout?.exercises?.map { it.toPresentation() }
                        ?: emptyList(),
                    isLoading = false
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun finishWorkout() {
        viewModelScope.launch {
            val endTime = System.currentTimeMillis()
            val duration = endTime - startTime

            val exerciseData: String = Json.encodeToString(state.value.exercises)

            val history = WorkoutHistory(
                workoutTitle = state.value.workoutTitle,
                timestamp = endTime,
                durationMillis = duration,
                exercisesJson = exerciseData
            )

            saveWorkoutHistoryUseCase.invoke(history)

            emitSideEffect(DetailsSideEffect.NavigateBack)
        }
    }

}
