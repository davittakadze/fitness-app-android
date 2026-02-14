package com.betteryou.workout.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.betteryou.workout.presentation.component.BetterYouBottomSheet
import com.betteryou.workout.presentation.screen.model.ExerciseUI
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.TBCTheme
import com.example.betteryou.core_ui.util.Radius
import com.example.betteryou.core_ui.util.components.AppButtonType
import com.example.betteryou.core_ui.util.components.TBCAppAsyncImage
import com.example.betteryou.core_ui.util.components.TBCAppButton
import com.example.betteryou.core_ui.util.components.TBCAppSearchField
import com.example.betteryou.core_ui.util.components.TBCAppTextField
import kotlinx.coroutines.launch

@Composable
fun WorkoutScreen(
    viewModel: WorkoutViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(WorkoutEvent.LoadWorkouts)
    }

    WorkoutContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WorkoutContent(
    state: WorkoutState,
    onEvent: (WorkoutEvent) -> Unit,
) {
    var showCreateSheet by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Workouts",
                        style = TBCTheme.typography.headlineLarge,
                        color = TBCTheme.colors.textPrimary
                    )
                },
                actions = {
                    IconButton(onClick = { showCreateSheet = true }) {
                        Icon(
                            painter = painterResource(R.drawable.plus_svgrepo_com_2),
                            contentDescription = "Add",
                            tint = TBCTheme.colors.textPrimary
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
        val tabs = state.tabs
        val pagerState = rememberPagerState(pageCount = { tabs.size })
        val scope = rememberCoroutineScope()
        if (showCreateSheet) {
            CreateWorkoutBottomSheet(
                state = state,
                onDismiss = { showCreateSheet = false },
                onEvent = onEvent,
                onSave = {}
            )
        }

        Box(
            modifier = Modifier
                .background(TBCTheme.colors.background)
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column {

                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    indicator = { tabPositions ->
                        if (pagerState.currentPage < tabPositions.size) {
                            TabRowDefaults.SecondaryIndicator(
                                modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                                color = TBCTheme.colors.accent
                            )
                        }
                    },
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = {
                                Text(
                                    text = title,
                                    style = TBCTheme.typography.bodyMedium,
                                    color = if (pagerState.currentPage == index) TBCTheme.colors.accent else TBCTheme.colors.textSecondary
                                )
                            }
                        )
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    userScrollEnabled = false
                ) { page ->
                    when (page) {
                        0 -> MyWorkoutsListScreen()
                        1 -> PreDefinedPlansScreen()
                    }
                }
            }
        }
    }

}

@Composable
private fun MyWorkoutsListScreen() {
    Text(text = "My Workouts", modifier = Modifier.padding(16.dp))
}

@Composable
private fun PreDefinedPlansScreen() {
    Text(text = "aeee", modifier = Modifier.padding(16.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateWorkoutBottomSheet(
    state: WorkoutState,
    onEvent: (WorkoutEvent) -> Unit,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
) {

    BetterYouBottomSheet(onDismissRequest = onDismiss) {
        // Name plan
        TBCAppTextField(
            value = state.workoutName,
            onValueChange = {
                onEvent(WorkoutEvent.OnExerciseNameChange(it))
            },
            placeholder = "Workout plan name",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Search field
        TBCAppSearchField(
            value = state.searchQuery,
            onValueChange = {
                onEvent(WorkoutEvent.OnSearchChange(it))
            },
            placeholder = "Search exercises...",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Workouts
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(state.filteredWorkouts, key = { workout -> workout.id }) { workout ->

                ExerciseSelectionRow(
                    workout = workout,
                    isSelected = workout.isSelected,
                    onSelect = { checked ->
                        onEvent.invoke(WorkoutEvent.OnSelectExercise(workout))
                    }
                )
            }
        }

        TBCAppButton(
            text = "Create Workout",
            onClick = {
                onDismiss()
                onEvent
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            type = AppButtonType.Primary,
            enabled = state.filteredWorkouts.any { it.isSelected }
        )
    }
}

@Composable
fun ExerciseSelectionRow(
    workout: ExerciseUI,
    isSelected: Boolean,
    onSelect: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = TBCTheme.colors

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clip(Radius.radius12)
            .background(
                if (isSelected) colors.accent.copy(alpha = 0.1f)
                else Color.Transparent
            )
            .clickable { onSelect(!isSelected) }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Image
        Box(
            modifier = Modifier
                .fillMaxWidth(0.35f)
                .aspectRatio(1.7f)
                .background(Color.Transparent, Radius.radius8),
            contentAlignment = Alignment.Center
        ) {
            TBCAppAsyncImage(
                img = workout.imageUrl,
                placeholder = R.drawable.icon_workout_screen,
                contentDescription = null,
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Name and Category
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = workout.name,
                style = TBCTheme.typography.bodyLarge,
                color = if (isSelected) colors.accent else colors.textPrimary
            )
            Text(
                text = workout.category,
                style = TBCTheme.typography.bodyMedium,
                color = colors.textSecondary
            )
        }

        Box(
            modifier = Modifier
                .size(24.dp)
                .background(Color.Transparent, CircleShape)
                .border(2.dp, if (isSelected) colors.accent else colors.border, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = colors.accent,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}
