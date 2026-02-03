package com.example.betteryou.domain.repository.login

import com.example.betteryou.domain.Resource
import kotlinx.coroutines.flow.Flow

interface LogInRepository {
    suspend fun logInUser(email: String, password: String): Flow<Resource<Unit>>
}