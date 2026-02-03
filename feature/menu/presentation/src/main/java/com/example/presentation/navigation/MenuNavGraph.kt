package com.example.presentation.navigation

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.betteryou.presentation.navigation.LogInRoute
import com.example.betteryou.presentation.navigation.MainRoute
import com.example.betteryou.presentation.navigation.MenuRoute
import com.example.betteryou.presentation.navigation.RegisterRoute
import com.example.presentation.MenuScreen
import com.example.presentation.MenuSideEffect
import com.example.presentation.MenuViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.serialization.Serializable

fun NavGraphBuilder.menuNavGraph(
    navController: NavController,
    googleLauncher: ActivityResultLauncher<Intent>,
    googleClient: GoogleSignInClient
) {
    composable<MenuRoute> {
        val viewModel: MenuViewModel = hiltViewModel()

        LaunchedEffect(Unit) {
            viewModel.sideEffect.collect { effect ->
                when (effect) {
                    MenuSideEffect.RequestGoogleSignIn -> {
                        googleLauncher.launch(googleClient.signInIntent)
                    }
                    MenuSideEffect.NavigateToHome -> {
                        navController.navigate(MainRoute) {
                            popUpTo(MenuRoute) { inclusive = true }
                        }
                    }
                    else -> Unit
                }
            }
        }

        GoogleTokenHolder.onToken = { token ->
            viewModel.onGoogleTokenReceived(token)
        }

        MenuScreen(
            onLoginClick = {
                navController.navigate(LogInRoute)
            },
            onRegisterClick = {
                navController.navigate(RegisterRoute)
            },
            onGoogleSignInClick = {
                navController.navigate(MainRoute)
            }
        )
    }
}

object GoogleTokenHolder {
    var onToken: ((String) -> Unit)? = null
}
