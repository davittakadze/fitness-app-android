package com.example.betteryou.feature.settings.presentation

sealed interface SettingsEvent {
    data object OnProfileClick : SettingsEvent

    data class OnDarkThemeChanged(val isEnabled: Boolean) : SettingsEvent
    data object OnLogOutClick : SettingsEvent
    data object OnDeleteAccountClick : SettingsEvent
    data object OnChangePasswordClick : SettingsEvent
}