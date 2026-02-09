package com.example.betteryou

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betteryou.domain.usecase.GetPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.example.betteryou.domain.common.DatastoreKeys.DARK_THEME_KEY


@HiltViewModel
class TBCAppViewModel @Inject constructor(
    private val getPreferencesUseCase: GetPreferencesUseCase
) : ViewModel() {

    var isDarkTheme by mutableStateOf(false)
        private set

    init {
        getPreferencesUseCase(DARK_THEME_KEY, false)
            .onEach { value ->
                isDarkTheme = value
            }.launchIn(viewModelScope)
    }
}
