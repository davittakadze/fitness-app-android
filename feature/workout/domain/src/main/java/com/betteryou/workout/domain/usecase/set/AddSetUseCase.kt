package com.betteryou.workout.domain.usecase.set

import com.betteryou.workout.domain.repository.ExerciseRepository
import javax.inject.Inject

class AddSetUseCase @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {
    suspend operator fun invoke(workoutExerciseId: String) {
        exerciseRepository.addEmptySet(workoutExerciseId)
    }
}