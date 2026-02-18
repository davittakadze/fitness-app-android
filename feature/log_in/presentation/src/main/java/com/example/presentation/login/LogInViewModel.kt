package com.example.presentation.login

import androidx.lifecycle.viewModelScope
import com.example.betteryou.presentation.common.BaseViewModel
import com.example.betteryou.presentation.common.UiText
import com.example.domain.usecase.login.LogInUseCase
import com.example.domain.usecase.validator.EmailValidatorUseCase
import com.example.domain.usecase.validator.EmptyFieldsValidatorUseCase
import com.example.domain.usecase.validator.PasswordValidatorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.betteryou.core_ui.R

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val logInUseCase: LogInUseCase,
    private val emailValidatorUseCase: EmailValidatorUseCase,
    private val passwordValidatorUseCase: PasswordValidatorUseCase,
    private val emptyFieldsValidatorUseCase: EmptyFieldsValidatorUseCase,
) : BaseViewModel<LogInState, LogInEvent, LogInSideEffect>(LogInState()) {
    override fun onEvent(event: LogInEvent) {
        when (event) {
            is LogInEvent.OnEmailChange -> updateState { copy(email = event.email) }
            is LogInEvent.OnPasswordChange ->
                updateState { copy(password = event.password) }

            is LogInEvent.OnLogInButtonClick -> logIn(event.email, event.password)
            is LogInEvent.OnBackButtonClick -> emitSideEffect(LogInSideEffect.NavigateBack)
            is LogInEvent.PasswordVisibilityChange -> updateState { copy(isPasswordVisible = event.isVisible) }
        }
    }

    private fun logIn(email: String, password: String) {
        val emptyFieldsError = emptyFieldsValidatorUseCase.invoke(email, password)
        val emailError = emailValidatorUseCase.invoke(email)
        val passwordError = passwordValidatorUseCase.invoke(password)

        val validationErrorResId = when {
            emptyFieldsError -> R.string.empty_fields
            !emailError -> R.string.invalid_email
            passwordError -> R.string.invalid_password
            else -> null
        }

        validationErrorResId?.let {
            emitSideEffect(
                LogInSideEffect.ShowError(
                    UiText.StringResource(
                        it
                    )
                )
            )
            return
        }


        viewModelScope.launch {
            handleResponse(
                apiCall = { logInUseCase.invoke(email = email, password = password) },
                onSuccess = {
                    updateState {
                        copy(isLoading = false)
                    }
                    emitSideEffect(LogInSideEffect.NavigateHome)
                },
                onError = { resource ->
                    updateState {
                        copy(isLoading = false)
                    }

                    emitSideEffect(
                        LogInSideEffect.ShowError(
                            UiText.DynamicString(resource)
                        )
                    )
                },
                onLoading = { loader ->
                    updateState {
                        copy(isLoading = loader.isLoading)
                    }
                }
            )
        }
    }
}

