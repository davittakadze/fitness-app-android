package com.betteryou.workout.domain.usecase.workout

import com.betteryou.workout.domain.repository.WorkoutsRepository
import com.bettetyou.core.model.Workout
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWorkoutDetailsUseCase @Inject constructor(
    private val repository: WorkoutsRepository
) {
    operator fun invoke(id: String): Flow<Workout?> {
        return repository.getWorkoutById(id)
    }
}