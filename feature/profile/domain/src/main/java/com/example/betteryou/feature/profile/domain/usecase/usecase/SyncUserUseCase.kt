package com.example.betteryou.feature.profile.domain.usecase.usecase

import com.example.betteryou.feature.profile.domain.repository.UserProfileRepository
import javax.inject.Inject

class SyncUserUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    suspend operator fun invoke() {
        repository.syncUserFromRemote()
    }
}