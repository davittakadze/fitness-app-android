package com.example.domain.usecase.validator

import javax.inject.Inject

class PasswordValidatorUseCase @Inject constructor() {
    operator fun invoke(password: String): Boolean = password.length < 6
}