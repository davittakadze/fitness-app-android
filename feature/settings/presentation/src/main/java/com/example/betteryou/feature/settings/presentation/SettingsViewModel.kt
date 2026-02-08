package com.example.betteryou.feature.settings.presentation

import com.example.betteryou.presentation.common.BaseViewModel
import javax.inject.Inject

class SettingsViewModel @Inject constructor() :
    BaseViewModel<SettingsState, SettingsEvent, SettingSideEffects>
        (SettingsState()) {
    override fun onEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.OnChangePasswordClick -> {

            }

            is SettingsEvent.OnDarkThemeChanged -> {
                updateState {
                    copy(isDarkThemeEnabled = event.isEnabled)
                }
            }

            SettingsEvent.OnDeleteAccountClick -> {

            }
            SettingsEvent.OnLogOutClick -> {

            }
            SettingsEvent.OnProfileClick -> {
                emitSideEffect(SettingSideEffects.NavigateToProfile)
            }
        }
    }
}