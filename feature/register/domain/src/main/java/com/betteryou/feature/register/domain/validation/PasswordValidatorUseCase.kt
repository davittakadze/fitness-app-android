package com.betteryou.feature.register.domain.validation

import javax.inject.Inject

class PasswordValidatorUseCase @Inject constructor() {
    operator fun invoke(password: String): Boolean = password.length < 6
}