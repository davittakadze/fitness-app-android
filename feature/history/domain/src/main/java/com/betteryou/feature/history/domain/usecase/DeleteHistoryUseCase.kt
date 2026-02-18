package com.betteryou.feature.history.domain.usecase

import com.betteryou.feature.history.domain.repository.HistoryRepository
import javax.inject.Inject

class DeleteHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(id: Long) = historyRepository.deleteHistory(id)

}