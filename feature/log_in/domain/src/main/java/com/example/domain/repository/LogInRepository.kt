package com.example.domain.repository

import com.example.betteryou.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface LogInRepository {
    suspend fun logInUser(email: String, password: String): Flow<Resource<Unit>>
}