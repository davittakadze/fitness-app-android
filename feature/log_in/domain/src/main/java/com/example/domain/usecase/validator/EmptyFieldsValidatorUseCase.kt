package com.example.domain.usecase.validator

import javax.inject.Inject

class EmptyFieldsValidatorUseCase @Inject constructor() {
    operator fun invoke(email: String, password: String): Boolean {
        return email.isBlank() || password.isBlank()
    }
}