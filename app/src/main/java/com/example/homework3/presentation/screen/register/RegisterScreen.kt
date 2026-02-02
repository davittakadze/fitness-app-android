package com.example.homework3.presentation.screen.register

import android.widget.Toast
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
import com.example.homework3.R
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.homework3.ui.theme.TBCTheme
import com.example.homework3.ui.theme.local_theme.LocalTBCColors
import com.example.homework3.ui.theme.local_theme.LocalTBCTypography
import com.example.homework3.ui.theme.util.Spacer
import com.example.homework3.ui.theme.util.components.TBCAppButton
import com.example.homework3.ui.theme.util.components.TBCAppPasswordField
import com.example.homework3.ui.theme.util.components.TBCAppTextField

@Composable
fun RegisterScreen(
    onRegisterClick: () -> Unit,
    onBackClick: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
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
                    Toast.makeText(
                        context,
                        effect.error,
                        Toast.LENGTH_SHORT
                    ).show()
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
