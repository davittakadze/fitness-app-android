package com.example.domain.repository.usecase.auth

import com.example.domain.repository.auth.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(): Boolean = repository.isLoggedIn()
}
