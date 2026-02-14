package com.betteryou.workout.domain.usecase

import com.betteryou.workout.domain.repository.WorkoutsRepository
import javax.inject.Inject

class GetWorkoutsUseCase @Inject constructor(private val repository: WorkoutsRepository) {
    suspend operator fun invoke() = repository.getWorkoutsFromApi()
}