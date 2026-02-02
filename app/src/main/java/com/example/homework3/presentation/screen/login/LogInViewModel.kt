package com.example.homework3.presentation.screen.login

import androidx.lifecycle.viewModelScope
import com.example.homework3.domain.common.Resource
import com.example.homework3.domain.usecase.login.LogInUseCase
import com.example.homework3.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val useCase: LogInUseCase
) : BaseViewModel<LogInState, LogInEvent, LogInSideEffect>(LogInState()) {
    override fun onEvent(event: LogInEvent) {
        when (event) {
            is LogInEvent.OnEmailChange -> updateState { copy(email = event.email) }
            is LogInEvent.OnPasswordChange ->
                updateState { copy(password = event.password) }
            is LogInEvent.OnLogInButtonClick -> logIn(event.email, event.password)
            is LogInEvent.OnBackButtonClick -> emitSideEffect(LogInSideEffect.NavigateBack)
            is LogInEvent.PasswordVisibilityChange -> updateState { copy(isPasswordVisible=event.isVisible) }
        }
    }

    private fun logIn(email: String, password: String) {
        viewModelScope.launch {

            useCase(email, password).collect { resource ->

                when (resource) {

                    is Resource.Loader -> {
                        updateState {
                            copy(isLoading = resource.isLoading)
                        }
                    }

                    is Resource.Success -> {
                        updateState {
                            copy(isLoading = false)
                        }

                        emitSideEffect(LogInSideEffect.NavigateHome)
                    }

                    is Resource.Error -> {
                        updateState {
                            copy(isLoading = false)
                        }
                        emitSideEffect(
                            LogInSideEffect.ShowError(
                                resource.errorMessage
                            )
                        )
                    }
                }
            }
        }
    }
}

