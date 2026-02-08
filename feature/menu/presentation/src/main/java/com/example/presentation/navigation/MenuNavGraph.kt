package com.example.presentation.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.betteryou.presentation.navigation.LogInRoute
import com.example.betteryou.presentation.navigation.MainRoute
import com.example.betteryou.presentation.navigation.MenuRoute
import com.example.betteryou.presentation.navigation.RegisterRoute
import com.example.betteryou.presentation.snackbar.SnackBarController
import com.example.betteryou.presentation.snackbar.SnackbarEvent
import com.example.presentation.MenuScreen
import com.example.presentation.MenuViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.launch

fun NavGraphBuilder.menuNavGraph(
    navController: NavController,
    googleClient: GoogleSignInClient,
) {
    composable<MenuRoute> {
        val viewModel: MenuViewModel = hiltViewModel()
        val context = LocalContext.current
        val scope = rememberCoroutineScope()

        val googleLauncher =
            rememberLauncherForActivityResult(
                ActivityResultContracts.StartActivityForResult()
            ) { result ->
                val data = result.data ?: return@rememberLauncherForActivityResult
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)

                try {
                    val account = task.getResult(ApiException::class.java)
                    val idToken = account.idToken

                    if (!idToken.isNullOrEmpty()) {
                        viewModel.onGoogleTokenReceived(idToken)
                    }
                } catch (e: ApiException) {
                    scope.launch {
                        SnackBarController.sendEvent(
                            SnackbarEvent(
                                message = context.getString(
                                    com.example.betteryou.core_res.R.string.google_sign_in_failed
                                )
                            )
                        )
                    }
                }
            }

        MenuScreen(
            viewModel = viewModel,

            onLoginClick = {
                navController.navigate(LogInRoute)
            },

            onRegisterClick = {
                navController.navigate(RegisterRoute)
            },

            onNavigateHome = {
                navController.navigate(MainRoute) {
                    popUpTo(MenuRoute) { inclusive = true }
                }
            },

            onRequestGoogleSignIn = {
                googleLauncher.launch(googleClient.signInIntent)
            }
        )
    }
}
