package com.example.betteryou.navigation

import android.annotation.SuppressLint
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
import com.example.betteryou.feature.profile.presentation.navigation.profileNavGraph
import com.example.betteryou.presentation.navigation.MainRoute
import com.example.betteryou.presentation.navigation.ProfileRoute
import com.example.betteryou.presentation.navigation.SplashRoute
import com.example.betteryou.presentation.screen.register.navigation.registerNavGraph
import com.example.presentation.login.navigation.logInNavGraph
import com.example.presentation.navigation.menuNavGraph
import com.example.presentation.splash.navigation.splashNavGraph
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

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
            menuNavGraph(navController, googleClient)
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
