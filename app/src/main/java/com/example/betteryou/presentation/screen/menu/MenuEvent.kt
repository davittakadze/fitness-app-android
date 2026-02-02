package com.example.betteryou.presentation.screen.menu

sealed interface MenuEvent {
    data object OnLogInClick: MenuEvent
    data object OnRegisterClick: MenuEvent
    data class OnGoogleRegisterClick(val token:String): MenuEvent
}
