package com.betteryou.workout.domain.usecase.workout

import com.betteryou.workout.domain.repository.WorkoutsRepository
import com.bettetyou.core.model.Workout
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllSavedWorkoutsUseCase @Inject constructor(
    private val repository: WorkoutsRepository
) {
    operator fun invoke(): Flow<List<Workout>> {
        return repository.getAllSavedWorkouts()
    }
}