package com.example.betteryou.presentation.screen.login

sealed interface LogInEvent {
    data class OnEmailChange(val email: String) : LogInEvent
    data class OnPasswordChange(val password: String) : LogInEvent
    data object OnBackButtonClick: LogInEvent
    data class OnLogInButtonClick(val email:String,val password:String):
        LogInEvent
    data class PasswordVisibilityChange(val isVisible:Boolean): LogInEvent
}

