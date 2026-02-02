package com.example.betteryou.presentation.screen.register

sealed interface RegisterSideEffect {
    data object NavigateBack: RegisterSideEffect
    data object NavigateToStep2: RegisterSideEffect
    data class ShowError(val error:String): RegisterSideEffect
}
