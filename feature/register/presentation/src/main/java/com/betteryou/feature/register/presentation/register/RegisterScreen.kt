package com.betteryou.feature.register.presentation.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.TBCTheme
import com.example.betteryou.core_ui.local_theme.LocalTBCColors
import com.example.betteryou.core_ui.local_theme.LocalTBCTypography
import com.example.betteryou.core_ui.util.Spacer
import com.example.betteryou.core_ui.util.components.TBCAppButton
import com.example.betteryou.core_ui.util.components.TBCAppPasswordField
import com.example.betteryou.core_ui.util.components.TBCAppTextField
import com.example.betteryou.presentation.snackbar.SnackBarController
import com.example.betteryou.presentation.snackbar.SnackbarEvent

@Composable
fun RegisterScreen(
    onRegisterClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                RegisterSideEffect.NavigateBack -> {
                    onBackClick()
                }

                RegisterSideEffect.NavigateToStep2 -> {
                    onRegisterClick()
                }

                is RegisterSideEffect.ShowError -> {
                    SnackBarController.sendEvent(
                        SnackbarEvent(
                            message = effect.error
                        )
                    )
                }
            }
        }
    }
    RegisterContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun RegisterContent(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(LocalTBCColors.current.background)
            .padding(24.dp, 48.dp)
    ) {
        IconButton(
            onClick = {
                onEvent(RegisterEvent.OnBackButtonClick)
            },
            Modifier.padding(4.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.left_arrow_svgrepo_com),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = LocalTBCColors.current.onBackground
            )
        }

        Spacer(modifier = Modifier.height(Spacer.spacing24))

        Text(
            text = stringResource(R.string.register),
            style = LocalTBCTypography.current.headlineLarge,
            color = LocalTBCColors.current.onBackground
        )

        Spacer(modifier = Modifier.height(Spacer.spacing24))

        TBCAppTextField(
            value = state.email,
            onValueChange = {
                onEvent(RegisterEvent.OnEmailChange(it))
            },
            placeholder = stringResource(R.string.email),
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        TBCAppPasswordField(
            value = state.password,
            isPasswordVisible = state.isPassword1Visible,
            onPasswordChange = {
                onEvent(RegisterEvent.OnPassword1Change(it))
            },
            onIconClick = {
                onEvent(RegisterEvent.OnIcon1Click(!state.isPassword1Visible))
            },
            placeholder = stringResource(R.string.password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Spacer.spacing16))

        TBCAppPasswordField(
            value = state.password2,
            isPasswordVisible = state.isPassword2Visible,
            onPasswordChange = {
                onEvent(RegisterEvent.OnPassword2Change(it))
            },
            onIconClick = {
                onEvent(RegisterEvent.OnIcon2Click(!state.isPassword2Visible))
            },
            placeholder = stringResource(R.string.repeat_password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Spacer.spacing24))

        TBCAppButton(
            text = stringResource(R.string.register).uppercase(),
            onClick = {
                onEvent(
                    RegisterEvent.OnNextButtonClick(
                        state.email,
                        state.password,
                        state.password2
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RegisterScreenPreview() {
    TBCTheme {
        RegisterContent(
            state = RegisterState(),
            onEvent = {}
        )
    }
}
