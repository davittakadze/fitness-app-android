package com.betteryou.feature.register.presentation.register

sealed interface RegisterSideEffect {
    data object NavigateBack: RegisterSideEffect
    data object NavigateToStep2: RegisterSideEffect
    data class ShowError(val error:String): RegisterSideEffect
}
