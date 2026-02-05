package com.example.domain.usecase.login

import com.example.betteryou.domain.common.Resource
import com.example.domain.repository.LogInRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val repository: LogInRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): Flow<Resource<Unit>> {
        return repository.logInUser(email, password)
    }

}