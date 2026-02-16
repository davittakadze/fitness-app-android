package com.betteryou.workout.domain.usecase.history

import com.betteryou.workout.domain.repository.WorkoutHistoryRepository
import com.bettetyou.core.model.WorkoutHistory
import javax.inject.Inject

class SaveWorkoutHistoryUseCase @Inject constructor(private val workoutHistoryRepository: WorkoutHistoryRepository) {
    suspend operator fun invoke(history: WorkoutHistory) = workoutHistoryRepository.saveToHistory(history)

}