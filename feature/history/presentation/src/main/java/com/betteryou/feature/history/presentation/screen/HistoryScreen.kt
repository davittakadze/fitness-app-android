package com.betteryou.feature.history.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.betteryou.feature.history.presentation.component.HistoryCard
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.core_ui.components.SwipeToDeleteContainer
import com.example.betteryou.core_ui.components.TBCAppScreenPlaceholder
import com.example.betteryou.presentation.extensions.CollectSideEffects
import com.example.betteryou.presentation.snackbar.SnackBarController
import com.example.betteryou.presentation.snackbar.SnackbarEvent

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    viewModel.sideEffect.CollectSideEffects {
        when (it) {
            is HistorySideEffect.ShowError -> {
                SnackBarController.sendEvent(
                    SnackbarEvent(
                        message = it.message.asString(context)
                    )
                )
            }

            HistorySideEffect.NavigateBack -> onBackClick()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(HistoryEvent.LoadHistory)
    }

    HistoryContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryContent(
    state: HistoryState,
    onEvent: (HistoryEvent) -> Unit,
) {
    Scaffold(
        containerColor = TBCTheme.colors.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "History",
                        style = TBCTheme.typography.headlineLarge,
                        color = TBCTheme.colors.textPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onEvent.invoke(HistoryEvent.OnBackClick) }) {
                        Icon(
                            painter = painterResource(R.drawable.left_arrow_svgrepo_com),
                            contentDescription = "Back",
                            tint = TBCTheme.colors.textPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = TBCTheme.colors.background,
                    titleContentColor = TBCTheme.colors.textPrimary
                )
            )
        }
    ) { paddingValues ->


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(TBCTheme.colors.background),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            if (state.history.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {

                        TBCAppScreenPlaceholder(
                            modifier = Modifier
                                .padding(top = 200.dp)
                                .fillMaxWidth(),
                            primaryText = "No Workouts Yet",
                            icon = R.drawable.ic_history,
                            secondaryText = "Your completed workouts will appear here.\nTime to hit the gym!"
                        )
                    }
                }
            }

            items(state.history, key = { it.id }) { historyItem ->
                SwipeToDeleteContainer(
                    onDelete = { onEvent.invoke(HistoryEvent.DeleteHistory(historyItem.id)) }
                ) {
                    HistoryCard(item = historyItem)
                }
            }
        }
    }
}


