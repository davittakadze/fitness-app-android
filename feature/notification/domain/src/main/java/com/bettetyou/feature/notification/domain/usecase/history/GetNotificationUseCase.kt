package com.bettetyou.feature.notification.domain.usecase.history

import com.bettetyou.feature.notification.domain.repository.NotificationRepository
import javax.inject.Inject

class GetNotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository
) {
    operator fun invoke() = notificationRepository.getNotifications()
}