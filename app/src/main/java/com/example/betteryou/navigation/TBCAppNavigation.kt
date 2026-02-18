package com.example.betteryou.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.betteryou.feature.history.presentation.navigation.historyNavGraph
import com.betteryou.feature.register.presentation.register.navigation.registerNavGraph
import com.betteryou.workout.presentation.navgraph.workoutNavGraph
import com.example.betteryou.feature.daily.presentation.navigation.dailyNavGraph
import com.example.betteryou.feature.profile.presentation.navigation.profileNavGraph
import com.example.betteryou.feature.recipes.presentation.navigation.recipesNavGraph
import com.example.betteryou.feature.settings.presentation.navigation.settingsNavGraph
import com.example.betteryou.presentation.navigation.DailyRoute
import com.example.betteryou.presentation.navigation.MainRoute
import com.example.betteryou.presentation.navigation.SplashRoute
import com.example.betteryou.presentation.snackbar.ObserveAsEvents
import com.example.betteryou.presentation.snackbar.SnackBarController
import com.example.presentation.login.navigation.logInNavGraph
import com.example.presentation.navigation.menuNavGraph
import com.example.presentation.splash.navigation.splashNavGraph
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TBCAppTheme() {
    val navController = rememberNavController()

    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val context = LocalContext.current

    val googleClient = remember {
        GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(com.example.betteryou.R.string.default_web_client_id))
                .requestEmail().build()
        )
    }

    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = (navBackStackEntry?.destination?.hierarchy?.any {
        it.route == MainRoute::class.qualifiedName
    } == true
            && currentRoute?.contains("WorkoutDetails") == false)
            && !currentRoute.contains("History")


    // Observe SnackBar events
    ObserveAsEvents(
        flow = SnackBarController.events, key = snackbarHostState
    ) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Short
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }



    Scaffold(bottomBar = {
        if (showBottomBar) {
            BottomBar(navController)
        }
    }, snackbarHost = {
        SnackbarHost(
            hostState = snackbarHostState
        )
    }) {
        NavHost(
            navController = navController,
            startDestination = SplashRoute,
        ) {
            splashNavGraph(navController)
            menuNavGraph(navController, googleClient)
            logInNavGraph(navController)
            registerNavGraph(navController)
            navigation<MainRoute>(
                startDestination = DailyRoute
            ) {
                settingsNavGraph(navController)
                profileNavGraph(navController)
                dailyNavGraph(navController)
                workoutNavGraph(navController)
                historyNavGraph(navController)
                recipesNavGraph(navController)
            }
        }
    }
}
