package com.betteryou.feature.register.domain.validation

import javax.inject.Inject

class EmptyFieldsValidatorUseCase @Inject constructor() {
    operator fun invoke(fields: List<String>): Boolean = fields.any { it.isBlank() }

}