package com.betteryou.feature.register.presentation.register.screen.account_creation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.betteryou.feature.register.presentation.register.common.RegisterEvent
import com.betteryou.feature.register.presentation.register.common.RegisterSideEffect
import com.betteryou.feature.register.presentation.register.common.RegisterState
import com.betteryou.feature.register.presentation.register.common.RegisterViewModel
import com.betteryou.feature.register.presentation.register.component.BaseScreenFlowContainer
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.core_ui.theme.Spacer
import com.example.betteryou.core_ui.components.button.TBCAppButton
import com.example.betteryou.core_ui.components.text_field.TBCAppPasswordField
import com.example.betteryou.core_ui.components.text_field.TBCAppTextField
import com.example.betteryou.presentation.extensions.CollectSideEffects
import com.example.betteryou.presentation.snackbar.SnackBarController
import com.example.betteryou.presentation.snackbar.SnackbarEvent

@Composable
fun AccountCreationScreen(
    viewModel: RegisterViewModel,
    onEvent: (RegisterEvent) -> Unit,
    navigateToHome: () -> Unit,
    onBack: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    viewModel.sideEffect.CollectSideEffects { effect ->
        when (effect) {
            is RegisterSideEffect.NavigateBack -> {
                onBack()
            }

            is RegisterSideEffect.NavigateToHome -> {
                navigateToHome()
            }

            is RegisterSideEffect.ShowError -> {
                SnackBarController.sendEvent(
                    SnackbarEvent(
                        message = effect.error.asString(context)
                    )
                )
            }
        }
    }

    AccountCreationContent(
        state = state,
        onEvent = onEvent
    )
}

@Composable
private fun AccountCreationContent(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
) {
    BaseScreenFlowContainer(
        title = R.string.account_creation_title,
        description = R.string.account_creation_description,
        onEvent = onEvent,
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            TBCAppTextField(
                value = state.email,
                onValueChange = {
                    onEvent(RegisterEvent.OnEmailChange(it))
                },
                placeholder = stringResource(R.string.email),
                keyboardType = KeyboardType.Email
            )

            // Password
            TBCAppPasswordField(
                value = state.password,
                isPasswordVisible = state.isPassword1Visible,
                onPasswordChange = {
                    onEvent(RegisterEvent.OnPasswordChange(it))
                },
                onIconClick = {
                    onEvent(RegisterEvent.OnTogglePassword1Visibility(!state.isPassword1Visible))
                },
                placeholder = stringResource(R.string.password),
                modifier = Modifier.fillMaxWidth()
            )

            // Repeat password
            TBCAppPasswordField(
                value = state.password2,
                isPasswordVisible = state.isPassword2Visible,
                onPasswordChange = {
                    onEvent(RegisterEvent.OnRepeatPasswordChange(it))
                },
                onIconClick = {
                    onEvent(RegisterEvent.OnTogglePassword2Visibility(!state.isPassword2Visible))
                },
                placeholder = stringResource(R.string.repeat_password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Spacer.spacing16))

            // Register button
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (state.loader) {
                    CircularProgressIndicator(
                        color = TBCTheme.colors.accent,
                        modifier = Modifier.size(48.dp)
                    )
                } else {
                    TBCAppButton(
                        text = stringResource(R.string.register).uppercase(),
                        onClick = {
                            onEvent(RegisterEvent.OnRegisterButtonClick)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}