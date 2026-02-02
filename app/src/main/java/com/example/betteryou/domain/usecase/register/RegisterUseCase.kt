package com.example.betteryou.domain.usecase.register

import com.example.betteryou.domain.common.Resource
import com.example.betteryou.domain.repository.register.RegisterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: RegisterRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        password2: String,
    ): Flow<Resource<Unit>> {
        if (email.isBlank() || password.isBlank()) {
            return flow {
                emit(
                    Resource.Error(
                        errorMessage = "Fill all of the fields!"
                    )
                )
            }
        }
        val emailRegex =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

        if (!email.matches(emailRegex)) {
            return flow {
                emit(
                    Resource.Error(
                        errorMessage = "Invalid email!"
                    )
                )
            }
        }

        if (password != password2) {
            return flow {
                emit(
                    Resource.Error(
                        errorMessage = "Passwords does not match!"
                    )
                )
            }
        }

        if (password.length < 6) {
            return flow {
                emit(
                    Resource.Error(
                        errorMessage = "Password must be at least 6 characters!"
                    )
                )
            }
        }
        return repository.registerUser(email, password)
    }
}