package com.example.betteryou.domain.usecase.auth

import com.example.betteryou.domain.repository.token.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val repo: AuthRepository
) {
    suspend operator fun invoke(): Boolean = repo.isLoggedIn()
}
