package com.example.betteryou.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.LocalTBCColors
import com.example.betteryou.presentation.navigation.DailyRoute
import com.example.betteryou.presentation.navigation.RecipesRoute
import com.example.betteryou.presentation.navigation.SettingsRoute
import com.example.betteryou.presentation.navigation.WorkoutRoute

@Composable
fun BottomBar(
    navController: NavController,
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    val tabs = listOf(
        BottomTab(DailyRoute, R.drawable.line_ascendant_graphic_of_zigzag_arrow_svgrepo_com),
        BottomTab(RecipesRoute,R.drawable.book_book_svgrepo_com),
        BottomTab(WorkoutRoute, R.drawable.icon_workout_screen),
        BottomTab(SettingsRoute, R.drawable.settings_svgrepo_com)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp
                )
            )
            .background(LocalTBCColors.current.surface)
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp
        ) {
            tabs.forEach { tab ->
                val isSelected = currentDestination?.hierarchy?.any {
                    it.route == tab.route::class.qualifiedName
                } == true

                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any {
                        it.route == tab.route::class.qualifiedName
                    } == true,
                    onClick = {
                        navController.navigate(tab.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(DailyRoute::class.qualifiedName ?: "") {
                                saveState = true
                            }
                        }
                    },
                    icon = {
                        Icon(
                            painter = painterResource(tab.iconRes),
                            contentDescription = null,
                            tint = if (isSelected)
                                LocalTBCColors.current.accent
                            else
                                LocalTBCColors.current.textSecondary
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = LocalTBCColors.current.accent,
                        unselectedIconColor = LocalTBCColors.current.textSecondary,
                        selectedTextColor = LocalTBCColors.current.accent,
                        indicatorColor = LocalTBCColors.current.surface
                    )
                )
            }
        }
    }
}
