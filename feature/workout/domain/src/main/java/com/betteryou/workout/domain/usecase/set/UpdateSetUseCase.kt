package com.betteryou.workout.domain.usecase.set

import com.betteryou.workout.domain.repository.ExerciseRepository
import com.betteryou.workout.domain.repository.WorkoutsRepository
import javax.inject.Inject

class UpdateSetUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {
    suspend operator fun invoke(setId: Long, reps: String, weight: String) {
        exerciseRepository.updateSetData(setId, reps, weight)
    }
}