package com.betteryou.feature.history.data.repository

import com.betteryou.feature.history.data.mapper.toDomain
import com.betteryou.feature.history.domain.model.GetHistory
import com.betteryou.feature.history.domain.repository.HistoryRepository
import com.example.betteryou.data.local.room.dao.history.HistoryDao
import com.example.betteryou.data.local.room.entity.workout.WorkoutHistoryEntity
import com.example.betteryou.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val historyDao: HistoryDao
) : HistoryRepository {
    override fun getAllWorkoutsHistory(): Flow<Resource<List<GetHistory>>> {
        return historyDao.getAllHistory()
            .map<List<WorkoutHistoryEntity>, Resource<List<GetHistory>>> { entities ->
                val domainModels = entities.map { entity ->
                    entity.toDomain()
                }
                Resource.Success(domainModels)
            }
            .onStart {
                emit(Resource.Loader(isLoading = true))
            }
            .catch { e ->
                emit(Resource.Error(e.localizedMessage ?: ""))
            }
    }

    override suspend fun deleteHistory(id: Long) {
        historyDao.deleteHistoryById(id)
    }


}


