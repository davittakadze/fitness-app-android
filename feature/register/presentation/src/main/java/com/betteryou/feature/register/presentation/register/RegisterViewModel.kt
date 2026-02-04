package com.betteryou.feature.register.presentation.register

import androidx.lifecycle.viewModelScope
import com.betteryou.feature.register.domain.usecase.RegisterUseCase
import com.example.betteryou.domain.common.Resource
import com.example.betteryou.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) :
    BaseViewModel<RegisterState, RegisterEvent, RegisterSideEffect>(RegisterState()) {
    override fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnBackButtonClick -> emitSideEffect(RegisterSideEffect.NavigateBack)
            is RegisterEvent.OnNextButtonClick -> validate(
                event.email,
                event.password,
                event.password2
            )

            is RegisterEvent.OnEmailChange -> updateState { copy(email = event.email) }
            is RegisterEvent.OnPassword1Change -> updateState { copy(password = event.password) }
            is RegisterEvent.OnPassword2Change -> updateState { copy(password2 = event.password) }
            is RegisterEvent.OnIcon1Click -> updateState { copy(isPassword1Visible = event.isVisible) }
            is RegisterEvent.OnIcon2Click -> updateState { copy(isPassword2Visible = event.isVisible) }
        }
    }

    private fun validate(email: String, password: String, password2: String) {
        viewModelScope.launch {
            registerUseCase(email, password, password2).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        emitSideEffect(RegisterSideEffect.ShowError(result.errorMessage))
                    }

                    is Resource.Loader -> {
                        updateState { copy(isLoading = result.isLoading) }
                    }

                    is Resource.Success -> {
                        updateState {
                            copy(isLoading = false)
                        }
                        emitSideEffect(RegisterSideEffect.NavigateToStep2)
                    }
                }
            }
        }
    }
}


