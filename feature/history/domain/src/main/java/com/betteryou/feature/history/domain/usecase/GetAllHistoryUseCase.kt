package com.betteryou.feature.history.domain.usecase

import com.betteryou.feature.history.domain.repository.HistoryRepository
import javax.inject.Inject

class GetAllHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    operator fun invoke(userId: String) = historyRepository.getAllWorkoutsHistory(userId = userId)
}