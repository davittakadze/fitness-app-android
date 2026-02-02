package com.example.homework3.domain.usecase.auth

import com.example.homework3.domain.repository.token.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(): Boolean = repo.isLoggedIn()
}
