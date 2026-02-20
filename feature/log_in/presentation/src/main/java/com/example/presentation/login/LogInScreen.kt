package com.example.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.core_ui.theme.LocalTBCColors
import com.example.betteryou.core_ui.theme.LocalTBCTypography
import com.example.betteryou.core_ui.theme.Spacer
import com.example.betteryou.core_ui.components.button.AppButtonType
import com.example.betteryou.core_ui.components.button.TBCAppButton
import com.example.betteryou.core_ui.components.text_field.TBCAppPasswordField
import com.example.betteryou.core_ui.components.text_field.TBCAppTextField
import com.example.betteryou.presentation.extensions.CollectSideEffects
import com.example.betteryou.presentation.snackbar.SnackBarController
import com.example.betteryou.presentation.snackbar.SnackbarEvent

@Composable
fun LogInScreen(
    onBackClick: () -> Unit,
    onLogInClick: () -> Unit,
    viewModel: LogInViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    viewModel.sideEffect.CollectSideEffects { effect ->
        when (effect) {

            LogInSideEffect.NavigateBack -> onBackClick()

            LogInSideEffect.NavigateHome -> onLogInClick()

            is LogInSideEffect.ShowError -> {
                SnackBarController.sendEvent(
                    SnackbarEvent(
                        message = effect.error.asString(context)
                    )
                )
            }
        }
    }

    LogInContent(
        viewModel::onEvent,
        state = state
    )
}

@Composable
fun LogInContent(
    onEvent: (LogInEvent) -> Unit,
    state: LogInState,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(LocalTBCColors.current.background)
            .padding(24.dp, 48.dp)
    ) {
        IconButton(
            onClick = {
                onEvent(LogInEvent.OnBackButtonClick)
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
            text = stringResource(R.string.login),
            style = LocalTBCTypography.current.headlineLarge,
            color=LocalTBCColors.current.onBackground
        )

        Spacer(modifier = Modifier.height(Spacer.spacing24))

        TBCAppTextField(
            value = state.email,
            onValueChange = { email ->
                onEvent(LogInEvent.OnEmailChange(email))
            },
            placeholder = stringResource(R.string.email),
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(Spacer.spacing16))

        TBCAppPasswordField(
            value = state.password,
            isPasswordVisible = state.isPasswordVisible,
            onPasswordChange = {
                onEvent(LogInEvent.OnPasswordChange(it))
            },
            onIconClick = {
                onEvent(LogInEvent.PasswordVisibilityChange(!state.isPasswordVisible))
            },
            placeholder = stringResource(R.string.password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(Spacer.spacing48))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    color = TBCTheme.colors.accent,
                    modifier = Modifier.size(48.dp)
                )
            } else {
                TBCAppButton(
                    text = stringResource(R.string.login).uppercase(),
                    onClick = { onEvent(LogInEvent.OnLogInButtonClick(state.email, state.password)) },
                    type = AppButtonType.Primary,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LogInScreenPreview() {
    TBCTheme {
        LogInContent({}, LogInState())
    }
}
