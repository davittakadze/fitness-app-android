package com.betteryou.feature.register.domain.usecase

import com.betteryou.feature.register.domain.model.RegisterUserInfo
import com.betteryou.feature.register.domain.repository.RegisterRepository
import com.example.betteryou.domain.common.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: RegisterRepository,
) {
    suspend operator fun invoke(
        user: RegisterUserInfo,
        email: String,
        password: String,
    ): Flow<Resource<Unit>> {
        return repository.registerUser(user = user, email = email, password = password)
    }
}