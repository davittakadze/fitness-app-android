package com.betteryou.feature.explore.domain.usecase

import com.betteryou.feature.explore.domain.repository.ExerciseRepository
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor(private val exerciseRepository: ExerciseRepository) {
    operator fun invoke(id: String) = exerciseRepository.getExerciseDetails(id)
}