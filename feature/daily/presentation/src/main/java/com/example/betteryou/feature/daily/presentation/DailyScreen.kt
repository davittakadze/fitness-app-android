package com.example.betteryou.feature.daily.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.LocalTBCColors
import com.example.betteryou.core_ui.theme.Spacer
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.feature.daily.presentation.component.ConsumedProductItem
import com.example.betteryou.feature.daily.presentation.component.GlassWaterTracker3D
import com.example.betteryou.feature.daily.presentation.component.MacroCircleChart
import com.example.betteryou.feature.daily.presentation.component.PageContent
import com.example.betteryou.feature.daily.presentation.component.TBCAppDropdown
import com.example.betteryou.feature.daily.presentation.component.bottom_sheet.BottomSheet
import com.example.betteryou.presentation.extensions.CollectSideEffects
import com.example.betteryou.presentation.snackbar.SnackBarController
import com.example.betteryou.presentation.snackbar.SnackbarEvent

@Composable
fun DailyScreen(
    viewModel: DailyViewModel = hiltViewModel(),
    navigateToNotifications: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val pagerState = rememberPagerState(
        initialPage = state.currentPage, pageCount = { 2 }
    )

    LaunchedEffect(pagerState.currentPage) {
        viewModel.onEvent(DailyEvent.ChangePage(pagerState.currentPage))
    }

    LaunchedEffect(state.currentPage) {
        if (pagerState.currentPage != state.currentPage) {
            pagerState.scrollToPage(state.currentPage)
        }
    }

    viewModel.sideEffect.CollectSideEffects { effect ->
        when (effect) {
            is DailySideEffect.ShowError -> {
                SnackBarController.sendEvent(
                    SnackbarEvent(
                        message = effect.error.asString(context)
                    )
                )
            }

            DailySideEffect.NavigateToNotifications -> navigateToNotifications()
        }
    }

    DailyScreenContent(state, pagerState, viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DailyScreenContent(
    state: DailyState,
    pagerState: PagerState,
    onEvent: (DailyEvent) -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.daily_screen_title),
                        style = TBCTheme.typography.headlineLarge,
                        color = TBCTheme.colors.onBackground,
                    )
                },
                actions = {
                    IconButton(onClick = { onEvent(DailyEvent.OnNavigateToNotifications) }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_notification),
                            contentDescription = "Notifications",
                            tint = TBCTheme.colors.textPrimary,
                            modifier = Modifier.size(30.dp)
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

        Box(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(LocalTBCColors.current.background)
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {

                item {
                    TBCAppDropdown(
                        state.products, onItemSelected = { product ->
                            onEvent(DailyEvent.OpenBottomSheet(product))
                        })
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 350.dp, max = 450.dp)
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxSize(),
                            key = { it },
                            contentPadding = PaddingValues(horizontal = 32.dp),
                            pageSpacing = 16.dp,
                            beyondViewportPageCount = 1
                        ) { page ->
                            val pageOffset =
                                (pagerState.currentPage - page + pagerState.currentPageOffsetFraction).coerceIn(
                                    -1f, 1f
                                )

                            when (page) {
                                0 -> PageContent(pageOffset = pageOffset) {
                                    MacroCircleChart(
                                        consumedCalories = state.consumedCalories,
                                        protein = state.protein,
                                        fat = state.fat,
                                        carbs = state.carbs,
                                        totalCaloriesGoal = state.totalCaloriesGoal,
                                        totalProteinGoal = state.totalProteinGoal,
                                        totalFatGoal = state.totalFatGoal,
                                        totalCarbsGoal = state.totalCarbsGoal
                                    )
                                }

                                1 -> PageContent(pageOffset = pageOffset) {
                                    GlassWaterTracker3D(
                                        currentWater = state.currentWater,
                                        waterGoal = state.waterGoal,
                                        onWaterChange = { newWater ->
                                            onEvent(DailyEvent.ChangeWater(newWater))
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                items(state.consumedProducts, key = { it.id }) { consumedProduct ->
                    ConsumedProductItem(consumedProduct) { item ->
                        onEvent(DailyEvent.DeleteProduct(item))
                    }
                }

                item { Spacer(Modifier.height(Spacer.spacing32)) }
            }

            if (state.isBottomSheetOpen && state.selectedProduct != null) {
                ModalBottomSheet(
                    onDismissRequest = { onEvent(DailyEvent.CloseBottomSheet) },
                    containerColor = TBCTheme.colors.background,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BottomSheet(
                        state.selectedProduct,
                        onClose = { onEvent(DailyEvent.CloseBottomSheet) },
                        addProductQuantity = { quantity, product ->
                            onEvent(
                                DailyEvent.AddProductQuantity(
                                    quantity = quantity, product = product
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

