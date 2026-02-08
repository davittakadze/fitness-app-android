package com.betteryou.feature.register.presentation.register

import com.betteryou.feature.register.domain.validation.RegisterValidationEnum

sealed interface RegisterSideEffect {
    data object NavigateBack: RegisterSideEffect
    data object NavigateToStep2: RegisterSideEffect
    data class ShowError(val error: String): RegisterSideEffect
}
