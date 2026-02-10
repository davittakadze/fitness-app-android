package com.betteryou.feature.register.domain.repository

import com.betteryou.feature.register.domain.model.RegisterUserInfo
import com.example.betteryou.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun registerUser(
        user: RegisterUserInfo,
        email: String,
        password: String,
    ): Flow<Resource<Unit>>
}