package com.example.domain.repository.usecase.login

import com.example.betteryou.domain.common.Resource
import com.example.domain.repository.login.LogInRepository
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
        if (email.isBlank() || password.isBlank()) {
            return flowOf(
                Resource.Error("Fields must not be empty")
            )
        }
        if (isEmailValid(email)) {
            return flowOf(
                Resource.Error("Invalid email format")
            )
        }
        if (password.length < 6) {
            return flowOf(
                Resource.Error("Password must be at least 6 characters")
            )
        }
        return repository.logInUser(email, password)
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
        return email.matches(emailRegex)
    }
}