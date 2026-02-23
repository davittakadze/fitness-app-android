package com.example.betteryou.feature.daily.domain.usecase.user

import com.example.betteryou.feature.daily.domain.repository.user.GetUserRepository
import javax.inject.Inject

class RefreshUserUseCase @Inject constructor(
    private val repository: GetUserRepository
) {
    suspend operator fun invoke() {
        repository.refreshUser()
    }
}