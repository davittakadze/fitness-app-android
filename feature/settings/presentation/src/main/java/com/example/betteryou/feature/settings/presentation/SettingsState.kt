package com.example.betteryou.feature.settings.presentation

data class SettingsState (
    val isLoading:Boolean=false,
    val isDarkThemeEnabled: Boolean = false,
    val isGeorgianLanguageEnabled:Boolean=false
)