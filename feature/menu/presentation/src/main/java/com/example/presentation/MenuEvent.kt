package com.example.presentation

sealed interface MenuEvent {
    data object OnLogInClick: MenuEvent
    data object OnRegisterClick: MenuEvent
    data object OnGoogleClick : MenuEvent
}
