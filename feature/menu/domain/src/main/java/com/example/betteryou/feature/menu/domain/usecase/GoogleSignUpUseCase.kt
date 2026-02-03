package com.example.betteryou.feature.menu.domain.usecase

import com.example.betteryou.feature.menu.domain.repository.GoogleSignUpRepository
import javax.inject.Inject

class GoogleSignUpUseCase @Inject constructor(
    private val repository: GoogleSignUpRepository
) {
    suspend operator fun invoke(idToken: String) = repository.signInWithGoogle(idToken)

}