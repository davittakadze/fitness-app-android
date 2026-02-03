package com.example.betteryou.domain.usecase.login

import android.util.Patterns
import com.example.betteryou.domain.Resource
import com.example.betteryou.domain.repository.login.LogInRepository
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
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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
}