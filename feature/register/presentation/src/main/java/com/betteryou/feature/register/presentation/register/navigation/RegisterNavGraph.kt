package com.betteryou.feature.register.presentation.register.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.betteryou.feature.register.presentation.register.common.RegisterViewModel
import com.betteryou.feature.register.presentation.register.screen.account_creation.AccountCreationScreen
import com.betteryou.feature.register.presentation.register.screen.basic_info.BasicInfoScreen
import com.betteryou.feature.register.presentation.register.screen.body_metrics.BodyMetricsScreen
import com.betteryou.feature.register.presentation.register.screen.goal_setting.GoalSettingScreen
import com.betteryou.feature.register.presentation.register.screen.user_activity.ActivityLevelScreen
import com.example.betteryou.presentation.navigation.Route
import com.example.betteryou.presentation.navigation.RegistrationRoute

fun NavGraphBuilder.registerNavGraph(navController: NavController) {
    navigation<RegistrationRoute.Graph>(
        startDestination = RegistrationRoute.ActivityLevel
    ) {
        composable<RegistrationRoute.ActivityLevel> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(RegistrationRoute.Graph)
            }
            val viewModel: RegisterViewModel = hiltViewModel(parentEntry)

            ActivityLevelScreen(
                viewModel = viewModel,
                onEvent = viewModel::onEvent,
                onNext = { navController.navigate(RegistrationRoute.BasicInfo) },
                onBack = { navController.popBackStack() }
            )
        }

        composable<RegistrationRoute.BasicInfo> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(RegistrationRoute.Graph)
            }
            val viewModel: RegisterViewModel = hiltViewModel(parentEntry)

            BasicInfoScreen(
                viewModel = viewModel,
                onEvent = viewModel::onEvent,
                onNext = { navController.navigate(RegistrationRoute.BodyMetrics) },
                onBack = { navController.popBackStack() }
            )
        }

        composable<RegistrationRoute.BodyMetrics> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(RegistrationRoute.Graph)
            }
            val viewModel: RegisterViewModel = hiltViewModel(parentEntry)

            BodyMetricsScreen(
                viewModel = viewModel,
                onEvent = viewModel::onEvent,
                onNext = { navController.navigate(RegistrationRoute.GoalSetting) },
                onBack = { navController.popBackStack() }
            )
        }

        composable<RegistrationRoute.GoalSetting> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(RegistrationRoute.Graph)
            }
            val viewModel: RegisterViewModel = hiltViewModel(parentEntry)

            GoalSettingScreen(
                viewModel = viewModel,
                onEvent = viewModel::onEvent,
                onNext = { navController.navigate(RegistrationRoute.AccountCreation) },
                onBack = { navController.popBackStack() }
            )
        }

        composable<RegistrationRoute.AccountCreation> { backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry(RegistrationRoute.Graph)
            }
            val viewModel: RegisterViewModel = hiltViewModel(parentEntry)

            AccountCreationScreen(
                viewModel = viewModel,
                onEvent = viewModel::onEvent,
                navigateToHome = { navController.navigate(Route.Main) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}