package com.example.betteryou

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betteryou.domain.usecase.GetPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.example.betteryou.domain.common.DatastoreKeys.DARK_THEME_KEY
import com.example.betteryou.domain.common.DatastoreKeys.USER_LANGUAGE_KEY

@HiltViewModel
class TBCAppViewModel @Inject constructor(
    getPreferencesUseCase: GetPreferencesUseCase,
) : ViewModel() {

    var isDarkTheme by mutableStateOf(false)
        private set

    var isGeorgianLanguage by mutableStateOf(false)
        private set

    init {
        getPreferencesUseCase(DARK_THEME_KEY, false)
            .onEach { value ->
                isDarkTheme = value
            }.launchIn(viewModelScope)

        getPreferencesUseCase(USER_LANGUAGE_KEY, "en")
            .onEach { langCode ->
                isGeorgianLanguage = langCode == "ka"

                val currentAppLocales = AppCompatDelegate.getApplicationLocales()

                if (currentAppLocales.toLanguageTags() != langCode) {
                    val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(langCode)
                    AppCompatDelegate.setApplicationLocales(appLocale)
                }
            }.launchIn(viewModelScope)
    }

}

