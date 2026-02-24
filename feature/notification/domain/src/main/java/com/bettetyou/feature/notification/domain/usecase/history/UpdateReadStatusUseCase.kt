package com.bettetyou.feature.notification.domain.usecase.history

import com.bettetyou.feature.notification.domain.repository.NotificationRepository
import javax.inject.Inject

class UpdateReadStatusUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke(id: Int, isRead: Boolean) =
        notificationRepository.updateReadStatus(id = id, isRead = isRead)
}