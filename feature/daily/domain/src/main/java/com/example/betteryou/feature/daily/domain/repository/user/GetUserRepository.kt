package com.example.betteryou.feature.daily.domain.repository.user

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.daily.domain.model.user.User
import kotlinx.coroutines.flow.Flow

interface GetUserRepository {
    fun getUser(): Flow<Resource<User>>
    suspend fun refreshUser()
}
