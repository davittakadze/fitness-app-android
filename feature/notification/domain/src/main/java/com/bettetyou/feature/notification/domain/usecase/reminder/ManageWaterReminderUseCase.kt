package com.bettetyou.feature.notification.domain.usecase.reminder

import com.bettetyou.feature.notification.domain.repository.WaterReminderScheduler
import com.example.betteryou.domain.common.DatastoreKeys
import com.example.betteryou.domain.repository.DatastoreRepository
import javax.inject.Inject

class ManageWaterReminderUseCase @Inject constructor(
    private val scheduler: WaterReminderScheduler,
    private val datastoreRepository: DatastoreRepository
) {
    suspend operator fun invoke(action: ReminderAction) {
        when (action) {
            ReminderAction.Enable -> {
                scheduler.schedule()
                datastoreRepository.setPreference(DatastoreKeys.WATER_REMINDER_ENABLED, true)
            }
            ReminderAction.Disable -> {
                scheduler.cancel()
                datastoreRepository.setPreference(DatastoreKeys.WATER_REMINDER_ENABLED, false)
            }
        }
    }

    sealed interface ReminderAction {
        data object Enable : ReminderAction
        data object Disable : ReminderAction
    }
}