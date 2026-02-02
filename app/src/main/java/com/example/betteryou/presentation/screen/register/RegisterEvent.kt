package com.example.betteryou.presentation.screen.register

sealed interface RegisterEvent {
    data class OnEmailChange(val email: String) : RegisterEvent
    data class OnPassword1Change(val password: String) : RegisterEvent
    data class OnPassword2Change(val password: String) : RegisterEvent
    data object OnBackButtonClick: RegisterEvent
    data class OnNextButtonClick(val email:String,val password:String,val password2:String): RegisterEvent
    data class OnIcon1Click(val isVisible:Boolean): RegisterEvent
    data class OnIcon2Click(val isVisible:Boolean): RegisterEvent
}

