package com.example.betteryou.navigation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.betteryou.presentation.screen.login.navigation.logInNavGraph
import com.example.betteryou.presentation.screen.register.navigation.registerNavGraph
import com.example.betteryou.presentation.screen.splash.navigation.SplashRoute
import com.example.betteryou.presentation.screen.splash.navigation.splashNavGraph
import kotlinx.serialization.Serializable
import com.example.betteryou.presentation.screen.profile.navigation.ProfileRoute
import com.example.betteryou.presentation.screen.profile.navigation.profileNavGraph
import com.example.presentation.navigation.menuNavGraph
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.example.presentation.navigation.GoogleTokenHolder
@Serializable
data object MainRoute

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TBCAppTheme() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val context = LocalContext.current

    val googleClient = remember {
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(com.example.betteryou.R.string.default_web_client_id))
                .requestEmail()
                .build()
        )
    }

    val googleLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data ?: return@rememberLauncherForActivityResult
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)
                val idToken = account.idToken

                if (!idToken.isNullOrEmpty()) {
                    GoogleTokenHolder.onToken?.invoke(idToken)
                }
            } catch (e: ApiException) {
                Toast.makeText(
                    context,
                    context.getString(com.example.betteryou.core_res.R.string.google_sign_in_failed),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    val showBottomBar = navBackStackEntry
        ?.destination
        ?.hierarchy
        ?.any { it.route == ProfileRoute::class.qualifiedName } == true



    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomBar(navController)
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = SplashRoute
        ) {
            splashNavGraph(navController)
            menuNavGraph(navController, googleLauncher, googleClient)
            logInNavGraph(navController)
            registerNavGraph(navController)
            navigation<MainRoute>(
                startDestination = ProfileRoute
            ) {
                profileNavGraph(navController)
            }
        }
    }
}
