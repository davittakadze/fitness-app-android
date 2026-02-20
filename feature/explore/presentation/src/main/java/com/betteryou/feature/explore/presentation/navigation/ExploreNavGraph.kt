package com.betteryou.feature.explore.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.betteryou.feature.explore.presentation.screen.details.DetailsScreen
import com.betteryou.feature.explore.presentation.screen.explore.ExploreScreen
import com.example.betteryou.presentation.navigation.Route

fun NavGraphBuilder.exploreNavGraph(navController: NavController) {

    composable<Route.Explore> {
        ExploreScreen(
            onNavigate = { workoutId ->
                navController.navigate(Route.ExploreDetails(workoutId = workoutId))
            }
        )

    }

    composable<Route.ExploreDetails> { entry ->
        val workoutId = entry.toRoute<Route.ExploreDetails>()

        DetailsScreen(workoutId =  workoutId.workoutId, onNavigate = { navController.popBackStack() })
    }
}