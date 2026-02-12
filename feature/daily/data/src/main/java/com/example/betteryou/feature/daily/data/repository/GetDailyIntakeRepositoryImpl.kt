package com.example.betteryou.feature.daily.data.repository

import com.example.betteryou.data.local.room.dao.intake.DailyIntakeDao
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.data.remote.mapper.toDomain
import com.example.betteryou.feature.daily.domain.model.Intake
import com.example.betteryou.feature.daily.domain.repository.intake.GetDailyIntakeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetDailyIntakeRepositoryImpl @Inject constructor(
    private val dao: DailyIntakeDao
) : GetDailyIntakeRepository {

    override fun getDailyIntake(userId: String, date: Long): Flow<Resource<Intake>> {
        return dao.getDailyIntake(userId, date)
            .map { entity ->
                if (entity != null) {
                    Resource.Success(entity.toDomain())
                } else {
                    Resource.Error("No intake found for this day")
                }
            }
            .catch { e ->
                emit(Resource.Error("Failed to fetch daily intake: ${e.message}"))
            }
    }
}