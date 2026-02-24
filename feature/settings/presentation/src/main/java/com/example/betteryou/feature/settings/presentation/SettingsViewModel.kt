package com.example.betteryou.feature.settings.presentation

import androidx.lifecycle.viewModelScope
import com.example.betteryou.domain.common.DatastoreKeys.DARK_THEME_KEY
import com.example.betteryou.domain.common.DatastoreKeys.USER_LANGUAGE_KEY
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.domain.usecase.GetPreferencesUseCase
import com.example.betteryou.domain.usecase.SetPreferencesUseCase
import com.example.betteryou.feature.settings.domain.usecase.delete_account.DeleteAccountUseCase
import com.example.betteryou.feature.settings.domain.usecase.logout.LogoutUseCase
import com.example.betteryou.presentation.common.BaseViewModel
import com.example.betteryou.presentation.common.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val setPreferencesUseCase: SetPreferencesUseCase,
    private val getPreferencesUseCase: GetPreferencesUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
) : BaseViewModel<SettingsState, SettingsEvent, SettingSideEffects>(SettingsState()) {

    init {
        observeTheme()
        observeLanguage()
    }

    override fun onEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.OnChangePasswordClick -> {}

            is SettingsEvent.OnDarkThemeChanged -> updateDarkTheme(event.isEnabled)
            SettingsEvent.OnDeleteAccountClick -> deleteAccount()
            SettingsEvent.OnLogOutClick -> logout()
            SettingsEvent.OnProfileClick -> emitSideEffect(SettingSideEffects.NavigateToProfile)
            SettingsEvent.OnHistoryClick -> emitSideEffect(SettingSideEffects.NavigateToHistory)
            SettingsEvent.OnFavoritesClick -> emitSideEffect(SettingSideEffects.NavigateToFavorites)
            is SettingsEvent.OnToggleLanguageClick -> updateLanguage(event.isEnabled)
            SettingsEvent.OnNotificationsClick -> emitSideEffect(SettingSideEffects.NavigateToNotifications)
        }
    }

    private fun updateDarkTheme(isEnabled: Boolean) {
        viewModelScope.launch {
            setPreferencesUseCase(DARK_THEME_KEY, isEnabled)
            updateState { copy(isDarkThemeEnabled = isEnabled) }
        }
    }

    private fun updateLanguage(isEnabled: Boolean) {
        viewModelScope.launch {
            setPreferencesUseCase(USER_LANGUAGE_KEY, if(isEnabled){"ka"}else{"en"})
            updateState { copy(isGeorgianLanguageEnabled = isEnabled) }
        }
    }

    private fun observeTheme() {
        viewModelScope.launch {
            getPreferencesUseCase(DARK_THEME_KEY, false)
                .collectLatest { dark ->
                    updateState { copy(isDarkThemeEnabled = dark) }
                }
        }
    }

    private fun observeLanguage() {
        viewModelScope.launch {
            getPreferencesUseCase(USER_LANGUAGE_KEY, "en")
                .collectLatest { lang ->
                    updateState { copy(isGeorgianLanguageEnabled = lang == "ka") }
                }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase().collectLatest { result ->
                when (result) {
                    is Resource.Loader -> {
                        updateState { copy(isLoading = result.isLoading) }
                    }

                    is Resource.Success -> {
                        emitSideEffect(SettingSideEffects.NavigateToMenu)
                    }

                    is Resource.Error -> {
                        emitSideEffect(
                            SettingSideEffects.ShowError(
                                UiText.DynamicString(result.errorMessage)
                            )
                        )
                    }
                }
            }
        }
    }

    private fun deleteAccount() {
        viewModelScope.launch {
            deleteAccountUseCase().collectLatest { result ->
                when (result) {
                    is Resource.Loader -> {
                        updateState { copy(isLoading = result.isLoading) }
                    }

                    is Resource.Success -> {
                        emitSideEffect(SettingSideEffects.NavigateToMenu)
                    }

                    is Resource.Error -> {
                        emitSideEffect(
                            SettingSideEffects.ShowError(
                                UiText.DynamicString(result.errorMessage)
                            )
                        )
                    }
                }
            }
        }
    }
}