package com.example.betteryou.feature.settings.domain.usecase.logout

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.settings.domain.repository.logout.LogoutRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: LogoutRepository,
) {
    suspend operator fun invoke(): Flow<Resource<Unit>> {
        return repository.logoutUser()
    }
}