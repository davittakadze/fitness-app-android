package com.example.homework3.domain.usecase.register

import com.example.homework3.domain.repository.register.GoogleSignUpRepository
import javax.inject.Inject

class GoogleSignUpUseCase @Inject constructor(
    private val repository: GoogleSignUpRepository
) {
    suspend operator fun invoke(idToken: String) = repository.signInWithGoogle(idToken)

}