package com.example.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.core_ui.theme.LocalTBCColors
import com.example.betteryou.core_ui.theme.LocalTBCTypography
import com.example.betteryou.core_ui.theme.Spacer
import com.example.betteryou.core_ui.components.button.AppButtonType
import com.example.betteryou.core_ui.components.button.TBCAppButton
import com.example.betteryou.core_ui.components.TBCAppCircularProgress
import com.example.betteryou.core_ui.components.button.TBCAppGoogleButton
import com.example.betteryou.presentation.extensions.CollectSideEffects
import com.example.betteryou.presentation.snackbar.SnackBarController
import com.example.betteryou.presentation.snackbar.SnackbarEvent

@Composable
fun MenuScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onNavigateHome: () -> Unit,
    onRequestGoogleSignIn: () -> Unit,
    viewModel: MenuViewModel,
) {
    val state by viewModel.state.collectAsState()
    LocalContext.current

    viewModel.sideEffect.CollectSideEffects { effect ->
        when (effect) {
            MenuSideEffect.NavigateToLogInPage -> onLoginClick()

            MenuSideEffect.NavigateToRegisterPage ->
                onRegisterClick()

            MenuSideEffect.NavigateToHome ->
                onNavigateHome()

            MenuSideEffect.RequestGoogleSignIn ->
                onRequestGoogleSignIn()

            is MenuSideEffect.ShowError -> {
                SnackBarController.sendEvent(
                    SnackbarEvent(effect.error)
                )
            }
        }
    }

    MenuContent(
        onEvent = viewModel::onEvent,
        state = state
    )
}

@Composable
private fun MenuContent(
    onEvent: (MenuEvent) -> Unit,
    state: MenuState,
) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalTBCColors.current.background)
                .padding(vertical = 40.dp, horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Row(
                Modifier
                    .offset((0).dp, (-32).dp)
                    .align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Image(
                        painter = painterResource(R.mipmap.better_you_logo_foreground),
                        contentDescription = null,
                        modifier = Modifier.wrapContentSize()
                    )
                }

                Spacer(modifier = Modifier.width(24.dp))

                Text(
                    text = stringResource(R.string.app_name),
                    style = LocalTBCTypography.current.headlineLarge,
                    color = LocalTBCColors.current.onBackground
                )
            }

            Spacer(modifier = Modifier.weight(1f))


            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TBCAppButton(
                    text = stringResource(R.string.login),
                    onClick = { onEvent(MenuEvent.OnLogInClick) },
                    type = AppButtonType.Outlined,
                    modifier = Modifier
                        .height(52.dp)
                        .weight(1f)
                )

                Spacer(modifier = Modifier.width(Spacer.spacing8))

                TBCAppButton(
                    text = stringResource(R.string.register),
                    onClick = { onEvent(MenuEvent.OnRegisterClick) },
                    type = AppButtonType.Outlined,
                    modifier = Modifier
                        .height(52.dp)
                        .weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(Spacer.spacing16))

            TBCAppGoogleButton(
                text = stringResource(R.string.google_sign_up),
                onClick = {
                    onEvent(MenuEvent.OnGoogleClick)
                },
                modifier = Modifier.fillMaxWidth()
            )

        }

        if (state.isLoading) {
            TBCAppCircularProgress(Modifier.align(Alignment.Center))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MenuScreenPreview() {
    TBCTheme {
        MenuContent({ }, MenuState())
    }
}
