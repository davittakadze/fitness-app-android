package com.betteryou.workout.data.repository

import com.betteryou.workout.data.remote.mapper.toEntity
import com.betteryou.workout.domain.repository.WorkoutHistoryRepository
import com.bettetyou.core.model.WorkoutHistory
import com.example.betteryou.data.local.room.dao.history.HistoryDao
import com.example.betteryou.data.local.room.dao.workout.WorkoutDao
import com.example.betteryou.data.local.room.entity.workout.WorkoutHistoryEntity
import javax.inject.Inject

class WorkoutHistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao
) : WorkoutHistoryRepository {

    override suspend fun saveToHistory(history: WorkoutHistory) {
        historyDao.insertHistory(history.toEntity())
    }
}