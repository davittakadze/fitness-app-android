package com.betteryou.workout.data.repository

import com.betteryou.workout.domain.repository.WorkoutHistoryRepository
import com.bettetyou.core.model.WorkoutHistory
import com.example.betteryou.data.local.room.dao.workout.WorkoutDao
import com.example.betteryou.data.local.room.entity.workout.WorkoutHistoryEntity
import javax.inject.Inject

class WorkoutHistoryRepositoryImpl @Inject constructor(
    private val workoutDao: WorkoutDao
) : WorkoutHistoryRepository {

    override suspend fun saveToHistory(history: WorkoutHistory) {
        val entity = WorkoutHistoryEntity(
            workoutTitle = history.workoutTitle,
            timestamp = history.timestamp,
            durationMillis = history.durationMillis,
            exercisesJson = history.exercisesJson
        )
        workoutDao.insertHistory(entity)
    }
}