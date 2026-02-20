package com.betteryou.feature.explore.presentation.screen.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.betteryou.feature.explore.presentation.model.ExerciseUI
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.components.TBCAppAsyncImage
import com.example.betteryou.core_ui.components.text_field.TBCAppSearchField
import com.example.betteryou.core_ui.theme.Radius
import com.example.betteryou.core_ui.theme.Spacer
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.presentation.extensions.CollectSideEffects
import com.example.betteryou.presentation.snackbar.SnackBarController
import com.example.betteryou.presentation.snackbar.SnackbarEvent

@Composable
fun ExploreScreen(
    viewModel: ExploreViewModel = hiltViewModel(),
    onNavigate: (workoutId: String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    viewModel.sideEffect.CollectSideEffects { effect ->
        when (effect) {
            is ExploreSideEffect.NavigateToDetails -> onNavigate(effect.workoutId)
            is ExploreSideEffect.ShowError -> {
                SnackBarController.sendEvent(
                    SnackbarEvent(
                        message = effect.message.asString(context)
                    )
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(ExploreEvent.FetchExercises)
    }

    ExploreScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreenContent(
    state: ExploreState,
    onEvent: (ExploreEvent) -> Unit,
) {
    Scaffold(
        containerColor = TBCTheme.colors.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Explore",
                        style = TBCTheme.typography.headlineLarge,
                        color = TBCTheme.colors.textPrimary
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = TBCTheme.colors.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TBCAppSearchField(
                value = state.searchInput,
                onValueChange = { onEvent(ExploreEvent.OnSearchFieldChange(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = "Search Exercise",
            )

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = TBCTheme.colors.textPrimary
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = state.filteredExercises,
                            key = { it.id }
                        ) { exercise ->
                            ExerciseCard(
                                exercise = exercise,
                                onClick = { onEvent(ExploreEvent.OnExerciseClick(exercise.id)) }
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(Spacer.spacing64))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseCard(
    exercise: ExerciseUI,
    onClick: () -> Unit,
) {
    Card(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.75f),
        shape = Radius.radius12,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = TBCTheme.colors.card,
            contentColor = TBCTheme.colors.textPrimary
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            TBCAppAsyncImage(
                img = exercise.imageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(Radius.radius12),
                placeholder = R.drawable.icon_workout_screen,
                contentScale = ContentScale.Fit,
            )

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = exercise.title,
                style = TBCTheme.typography.bodyMedium,
                color = TBCTheme.colors.textPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}