package com.betteryou.feature.history.domain.repository

import com.betteryou.feature.history.domain.model.GetHistory
import com.example.betteryou.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getAllWorkoutsHistory(userId: String): Flow<Resource<List<GetHistory>>>
    suspend fun deleteHistory(id: Long)
}