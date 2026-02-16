package com.betteryou.workout.data.repository

import com.betteryou.workout.domain.repository.ExerciseRepository
import com.example.betteryou.data.local.room.dao.workout.WorkoutDao
import com.example.betteryou.data.local.room.entity.workout.ExerciseSetEntity
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val workoutDao: WorkoutDao
) : ExerciseRepository {

    override suspend fun addEmptySet(workoutExerciseId: String) {
        workoutDao.insertSet(
            ExerciseSetEntity(
                workoutExerciseId = workoutExerciseId,
                reps = "",
                weight = "",
                isCompleted = false
            )
        )
    }

    override suspend fun removeSet(setId: Long) {
        workoutDao.deleteSet(ExerciseSetEntity(setId = setId, workoutExerciseId = ""))
    }

    override suspend fun updateSetData(setId: Long, reps: String, weight: String) {
        workoutDao.updateSet(setId, weight, reps)
    }
}