package com.bettetyou.feature.notification.domain.usecase.history

import com.bettetyou.feature.notification.domain.repository.NotificationRepository
import javax.inject.Inject

class DeleteNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    suspend operator fun invoke(id: Int) = notificationRepository.deleteNotificationById(id)

}