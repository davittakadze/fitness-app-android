package com.example.presentation

import androidx.lifecycle.viewModelScope
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.feature.menu.domain.usecase.GoogleSignUpUseCase
import com.example.betteryou.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val googleSignUpUseCase: GoogleSignUpUseCase
) : BaseViewModel<MenuState, MenuEvent, MenuSideEffect>(MenuState()) {
    override fun onEvent(event: MenuEvent) {
        when (event) {
            is MenuEvent.OnRegisterClick -> emitSideEffect(MenuSideEffect.NavigateToRegisterPage)
            is MenuEvent.OnLogInClick -> emitSideEffect(MenuSideEffect.NavigateToLogInPage)
            is MenuEvent.OnGoogleClick -> emitSideEffect(MenuSideEffect.RequestGoogleSignIn)
        }
    }

    fun onGoogleTokenReceived(idToken: String) {
        viewModelScope.launch {
            googleSignUpUseCase(idToken)
                .collectLatest { result ->
                    when (result) {
                        is Resource.Error ->
                            emitSideEffect(MenuSideEffect.ShowError(result.errorMessage))

                        is Resource.Loader ->
                            updateState { copy(isLoading = result.isLoading) }

                        is Resource.Success -> {
                            updateState { copy(isLoading = false) }
                            emitSideEffect(MenuSideEffect.NavigateToHome)
                        }
                    }
                }
        }
    }
}
