package com.bettetyou.feature.notification.domain.usecase.reminder

import com.example.betteryou.domain.common.DatastoreKeys
import com.example.betteryou.domain.repository.DatastoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWaterReminderStatusUseCase @Inject constructor(
    private val datastoreRepository: DatastoreRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return datastoreRepository.getPreference(
            key = DatastoreKeys.WATER_REMINDER_ENABLED,
            defaultValue = false
        )
    }
}