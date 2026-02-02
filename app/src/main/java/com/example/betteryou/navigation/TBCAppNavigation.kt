package com.example.betteryou.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.betteryou.presentation.screen.login.navigation.logInNavGraph
import com.example.betteryou.presentation.screen.menu.navigation.menuNavGraph
import com.example.betteryou.presentation.screen.register.navigation.registerNavGraph
import com.example.betteryou.presentation.screen.splash.navigation.SplashRoute
import com.example.betteryou.presentation.screen.splash.navigation.splashNavGraph
import kotlinx.serialization.Serializable
import com.example.betteryou.presentation.screen.profile.navigation.ProfileRoute
import com.example.betteryou.presentation.screen.profile.navigation.profileNavGraph

@Serializable
data object MainRoute

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TBCAppTheme() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

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
            menuNavGraph(navController)
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
