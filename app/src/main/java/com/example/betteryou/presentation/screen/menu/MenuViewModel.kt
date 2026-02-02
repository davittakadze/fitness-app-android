package com.example.betteryou.presentation.screen.menu

import androidx.lifecycle.viewModelScope
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.domain.usecase.register.GoogleSignUpUseCase
import com.example.betteryou.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
            is MenuEvent.OnGoogleRegisterClick -> signInWithGoogle(event.token)
        }
    }
    private fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            googleSignUpUseCase(idToken)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> emitSideEffect(MenuSideEffect.ShowError(result.errorMessage))
                        is Resource.Loader -> {
                            updateState { copy(isLoading = result.isLoading) }
                        }

                        is Resource.Success -> {
                            updateState {
                                copy(isLoading = false)
                            }
                            emitSideEffect(MenuSideEffect.NavigateToHome)
                        }
                    }
                }
        }
    }
}
