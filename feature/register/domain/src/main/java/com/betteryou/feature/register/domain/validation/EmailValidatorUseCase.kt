package com.betteryou.feature.register.domain.validation

import javax.inject.Inject

class EmailValidatorUseCase @Inject constructor() {

    operator fun invoke(email: String): Boolean {
        return email.isNotBlank() && EMAIL_REGEX.matches(email)
    }

    companion object {
        private val EMAIL_REGEX = Regex(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
        )
    }
}