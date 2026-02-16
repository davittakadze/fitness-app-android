package com.betteryou.workout.domain.repository

import com.bettetyou.core.model.WorkoutHistory

interface WorkoutHistoryRepository {
    suspend fun saveToHistory(history: WorkoutHistory)
}