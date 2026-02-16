package com.betteryou.workout.domain.usecase.workout

import com.betteryou.workout.domain.repository.WorkoutsRepository
import com.bettetyou.core.model.GetExercise
import javax.inject.Inject

class SaveWorkoutUseCase @Inject constructor(
    private val repository: WorkoutsRepository
) {
    suspend operator fun invoke(title: String, exercises: List<GetExercise>): String {
        return repository.saveWorkout(title, exercises)
    }
}