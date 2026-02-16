package com.betteryou.workout.domain.usecase.workout

import com.betteryou.workout.domain.repository.WorkoutsRepository
import javax.inject.Inject

class GetExercisesUseCase @Inject constructor(private val repository: WorkoutsRepository) {
    suspend operator fun invoke() = repository.getWorkoutsFromApi()
}