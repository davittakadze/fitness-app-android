package com.example.betteryou.feature.settings.domain.repository.logout

import com.example.betteryou.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface LogoutRepository {
    suspend fun logoutUser(): Flow<Resource<Unit>>
}