package com.example.betteryou.presentation.screen.menu

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.betteryou.R
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.betteryou.ui.theme.TBCTheme
import com.example.betteryou.ui.theme.local_theme.LocalTBCColors
import com.example.betteryou.ui.theme.local_theme.LocalTBCTypography
import com.example.betteryou.ui.theme.util.components.AppButtonType
import com.example.betteryou.ui.theme.util.Spacer
import com.example.betteryou.ui.theme.util.components.TBCAppButton
import com.example.betteryou.ui.theme.util.components.TBCAppCircularProgress
import com.example.betteryou.ui.theme.util.components.TBCAppGoogleButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@Composable
fun MenuScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    viewModel: MenuViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current

    val googleClient = rememberGoogleSignInClient(context)
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data ?: return@rememberLauncherForActivityResult
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)

        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken

            if (!idToken.isNullOrEmpty()) {
                viewModel.onEvent(
                    MenuEvent.OnGoogleRegisterClick(idToken)
                )
            }
        } catch (e: ApiException) {
            Toast.makeText(
                context,
                e.localizedMessage ?: context.getString(R.string.google_sign_in_failed),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {

                is MenuSideEffect.NavigateToLogInPage -> {
                    onLoginClick()
                }

                is MenuSideEffect.NavigateToRegisterPage -> {
                    onRegisterClick()
                }

                is MenuSideEffect.NavigateToHome -> onGoogleSignInClick()
                is MenuSideEffect.ShowError ->
                    Toast.makeText(
                        context,
                        effect.error,
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
    }

    MenuContent(
        onEvent = viewModel::onEvent,
        state = state,
        onGoogleClick = {
            launcher.launch(googleClient.signInIntent)
        }
    )
}

@Composable
private fun MenuContent(
    onEvent: (MenuEvent) -> Unit,
    state: MenuState,
    onGoogleClick: () -> Unit,
) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalTBCColors.current.background)
                .padding(vertical=40.dp,horizontal=24.dp)
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
                        painter = painterResource(R.drawable.guardian_svgrepo_com),
                        contentDescription = null,
                        modifier = Modifier.wrapContentSize()
                    )
                }

                Spacer(modifier = Modifier.width(24.dp))

                Text(
                    text = stringResource(R.string.application_name),
                    style = LocalTBCTypography.current.headlineLarge,
                    color = LocalTBCColors.current.onBackground
                )
            }

            Spacer(modifier = Modifier.weight(1f))

  //          Spacer(modifier = Modifier.height(Spacer.spacing16))

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
                    onGoogleClick()
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
fun rememberGoogleSignInClient(context: Context): GoogleSignInClient {
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
    return remember { GoogleSignIn.getClient(context, gso) }
}


@Composable
@Preview(showBackground = true)
fun MenuScreenPreview() {
    TBCTheme {
        MenuContent({ }, MenuState(), {})
    }
}
