package com.betteryou.feature.explore.domain.usecase

import com.betteryou.feature.explore.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetExercisesUseCase @Inject constructor(private val exerciseRepository: ExerciseRepository) {
    suspend operator fun invoke() = exerciseRepository.getExercises()
}