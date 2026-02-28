package com.example.betteryou.feature.favorites_page.presentation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.components.SwipeToDeleteContainer
import com.example.betteryou.core_ui.components.TBCAppAsyncImage
import com.example.betteryou.core_ui.components.TBCAppScreenPlaceholder
import com.example.betteryou.core_ui.theme.Radius
import com.example.betteryou.core_ui.theme.Spacer
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.feature.favorites_page.presentation.model.FavoriteMealUi
import com.example.betteryou.presentation.extensions.CollectSideEffects
import com.example.betteryou.presentation.snackbar.SnackBarController
import com.example.betteryou.presentation.snackbar.SnackbarEvent

@Composable
fun FavoritesScreen(onNavigateBack: () -> Unit, viewModel: FavoritesViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    FavoritesContent(state, viewModel::onEvent)
    viewModel.sideEffect.CollectSideEffects { effect ->
        when (effect) {
            FavoritesSideEffect.NavigateBack -> {
                onNavigateBack()
            }

            is FavoritesSideEffect.ShowError -> {
                SnackBarController.sendEvent(
                    SnackbarEvent(
                        message = effect.message.asString(context)
                    )
                )
            }
        }
    }
    FavoritesContent(state, viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesContent(state: FavoritesState, onEvent: (FavoritesEvent) -> Unit) {
    Scaffold(
        containerColor = TBCTheme.colors.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.favorites_title),
                        style = TBCTheme.typography.headlineLarge,
                        color = TBCTheme.colors.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onEvent(FavoritesEvent.OnBackClick) }) {
                        Icon(
                            painter = painterResource(R.drawable.left_arrow_svgrepo_com),
                            contentDescription = null,
                            tint = TBCTheme.colors.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = TBCTheme.colors.background,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        Box(Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(TBCTheme.colors.background)
                    .padding(bottom = 100.dp)
            ) {

                Spacer(Modifier.height(Spacer.spacing12))

                LazyColumn(
                    modifier = Modifier.padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (state.favouriteMeals.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                TBCAppScreenPlaceholder(
                                    modifier = Modifier
                                        .padding(top = 200.dp)
                                        .fillMaxWidth(),
                                    primaryText = stringResource(R.string.no_favorites),
                                    icon = R.drawable.heart_shape_svgrepo_com,
                                    secondaryText = "Favorites will appear here"
                                )
                            }
                        }
                    } else {
                        items(items = state.favouriteMeals, key = { it.id }) { item ->
                            SwipeToDeleteContainer(
                                onDelete = { onEvent.invoke(FavoritesEvent.RemoveFavorite(item.id)) }
                            ) {
                                MealItem(
                                    item = item,
                                    onItemClick = { onEvent(FavoritesEvent.OnItemClick(item)) }
                                )
                            }
                        }
                    }
                }

            }
            if (state.selectedMeal != null) {
                ModalBottomSheet(
                    onDismissRequest = { onEvent(FavoritesEvent.OnDismissSheet) },
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = TBCTheme.colors.background
                ) {
                    BottomSheet(item = state.selectedMeal)
                }
            }
        }
    }
}

@Composable
fun MealItem(
    item: FavoriteMealUi,
    onItemClick: () -> Unit,
) {
    Box(
        Modifier
            .fillMaxWidth()
            .height(125.dp)
            .clip(Radius.radius12)
            .clickable(
                onClick = {
                    onItemClick()
                }
            )
    ) {
        Row {
            TBCAppAsyncImage(
                img = item.imageUrl,
                modifier = Modifier.size(125.dp),
                placeholder = R.drawable.ic_launcher_background
            )
            Spacer(Modifier.width(Spacer.spacing12))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = TBCTheme.typography.bodyLarge,
                    color = TBCTheme.colors.onBackground
                )
                Spacer(Modifier.height(Spacer.spacing8))
                Text(
                    text = stringResource(R.string.ingredient_count, item.ingredientCount),
                    style = TBCTheme.typography.bodySmallest,
                    color = TBCTheme.colors.onBackground
                )
                Spacer(Modifier.height(Spacer.spacing8))
                Text(
                    text = stringResource(R.string.cooking_time, item.cookingTime),
                    style = TBCTheme.typography.bodySmallest,
                    color = TBCTheme.colors.onBackground
                )
                Spacer(Modifier.height(Spacer.spacing8))
                Text(
                    text = stringResource(R.string.difficulty, item.difficulty),
                    style = TBCTheme.typography.bodySmallest,
                    color = TBCTheme.colors.onBackground
                )
            }
        }
    }
}

@Composable
fun BottomSheet(
    item: FavoriteMealUi,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 600.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TBCAppAsyncImage(
            img = item.imageUrl,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            placeholder = R.drawable.icon_workout_screen
        )

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            InfoCard(item)

            Spacer(Modifier.height(20.dp))

            Text(
                text = item.title,
                style = TBCTheme.typography.headlineMedium,
                color = TBCTheme.colors.onBackground
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.ingredients),
                style = TBCTheme.typography.headlineMedium,
                color = TBCTheme.colors.onBackground
            )

            Spacer(Modifier.height(12.dp))

            (item.ingredients).forEach { item ->
                Text(
                    text = item,
                    style = TBCTheme.typography.bodyMedium,
                    color = TBCTheme.colors.onBackground
                )
                Spacer(Modifier.height(Spacer.spacing4))
            }
            Spacer(Modifier.height(Spacer.spacing12))
            Text(
                text = stringResource(R.string.recipe),
                style = TBCTheme.typography.headlineMedium,
                color = TBCTheme.colors.onBackground
            )
            Spacer(Modifier.height(Spacer.spacing12))
            Text(
                text = item.recipe,
                style = TBCTheme.typography.bodyMedium,
                color = TBCTheme.colors.onBackground
            )
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun InfoCard(item: FavoriteMealUi) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Radius.radius16)
            .border(1.dp, TBCTheme.colors.border, Radius.radius16)
            .background(TBCTheme.colors.border.copy(alpha = 0.2f))
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.weight(1f))
        InfoItem(
            stringResource(R.string.difficulty_without_dots),
            item.difficulty,
            R.drawable.chef_svgrepo_com
        )
        Spacer(Modifier.weight(1f))
        InfoItem(
            stringResource(R.string.cooking_time_without_dots),
            item.cookingTime,
            R.drawable.timer_svgrepo_com
        )
        Spacer(Modifier.weight(1f))
        InfoItem(
            stringResource(R.string.ingredient_count_without_dots),
            item.ingredientCount.toString(),
            R.drawable.cooking_food_in_a_hot_casserole_svgrepo_com
        )
        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun InfoItem(title: String, value: String, @DrawableRes icon: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = TBCTheme.colors.onBackground,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = title,
            color = TBCTheme.colors.onBackground,
            style = TBCTheme.typography.bodySmallest
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = value,
            color = TBCTheme.colors.onBackground,
            style = TBCTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
fun FavoritesScreenPreview() {
    FavoritesContent(FavoritesState()) {}
}