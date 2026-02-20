package com.betteryou.feature.explore.presentation.screen.details

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.components.TBCAppAsyncImage
import com.example.betteryou.core_ui.theme.Spacer
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.presentation.extensions.CollectSideEffects
import com.example.betteryou.presentation.snackbar.SnackBarController
import com.example.betteryou.presentation.snackbar.SnackbarEvent

@Composable
fun DetailsScreen(
    workoutId: String,
    viewModel: DetailsViewModel = hiltViewModel(),
    onNavigate: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    viewModel.sideEffect.CollectSideEffects { effect ->
        when (effect) {
            is DetailsSideEffect.NavigateBack -> onNavigate()
            is DetailsSideEffect.ShowError -> {
                SnackBarController.sendEvent(
                    SnackbarEvent(
                        message = effect.message.asString(context)
                    )
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(DetailsEvent.FetchDetails(workoutId))
    }

    DetailsContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsContent(
    state: DetailsState,
    onEvent: (DetailsEvent) -> Unit,
) {
    val exercise = state.exercise

    Scaffold(
        containerColor = TBCTheme.colors.card,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "",
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = TBCTheme.colors.background
                ),
                navigationIcon = {
                    IconButton(onClick = { onEvent(DetailsEvent.NavigateBack) }) {
                        Icon(
                            painter = painterResource(R.drawable.left_arrow_svgrepo_com),
                            contentDescription = "Back",
                            tint = TBCTheme.colors.textPrimary
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(TBCTheme.colors.background)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (exercise != null) {
                Column(modifier = Modifier.fillMaxSize()) {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        TBCAppAsyncImage(
                            img = exercise.imageUrl,
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .aspectRatio(1.2f),
                            contentScale = ContentScale.Fit,
                            placeholder = R.drawable.icon_workout_screen
                        )
                    }

                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = exercise.title,
                        color = TBCTheme.colors.textPrimary,
                        style = TBCTheme.typography.headlineLarge
                    )

                    Spacer(modifier = Modifier.height(Spacer.spacing12))

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1.2f),
                        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                        color = TBCTheme.colors.card
                    ) {
                        LazyColumn(
                            modifier = Modifier.padding(24.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            item {
                                Text(
                                    text = "Instructions",
                                    style = TBCTheme.typography.bodyMedium,
                                    color = TBCTheme.colors.textPrimary
                                )
                            }

                            item {
                                Text(
                                    text = exercise.description,
                                    style = TBCTheme.typography.bodyLarge,
                                    color = TBCTheme.colors.textSecondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}