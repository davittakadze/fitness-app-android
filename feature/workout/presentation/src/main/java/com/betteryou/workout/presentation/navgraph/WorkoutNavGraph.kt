package com.betteryou.workout.presentation.navgraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.betteryou.workout.presentation.screen.details.DetailsScreen
import com.betteryou.workout.presentation.screen.workout.WorkoutScreen
import com.example.betteryou.presentation.navigation.Route

fun NavGraphBuilder.workoutNavGraph(navController: NavController) {
    composable<Route.Workout> {
        WorkoutScreen(
            onNavigate = { workoutId ->
                navController.navigate(Route.WorkoutDetails(workoutId = workoutId))
            }
        )
    }

    composable<Route.WorkoutDetails> { backStackEntry ->
        val details: Route.WorkoutDetails = backStackEntry.toRoute()
        DetailsScreen(
            workoutId = details.workoutId,
            onBackClick = { navController.popBackStack() }
        )
    }
}