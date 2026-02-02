package com.example.homework3.domain.repository.login

import com.example.homework3.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface LogInRepository {
    suspend fun logInUser(email: String, password: String): Flow<Resource<Unit>>
}