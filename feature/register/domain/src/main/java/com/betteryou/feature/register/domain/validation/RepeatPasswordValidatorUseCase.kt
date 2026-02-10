package com.betteryou.feature.register.domain.validation

import javax.inject.Inject

class RepeatPasswordValidatorUseCase @Inject constructor() {
    operator fun invoke(password: String, repeatPassword: String): Boolean {
        return password == repeatPassword
    }
}