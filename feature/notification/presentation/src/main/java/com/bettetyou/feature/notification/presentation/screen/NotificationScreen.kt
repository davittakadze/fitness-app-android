package com.bettetyou.feature.notification.presentation.screen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bettetyou.feature.notification.presentation.component.page.HistoryPage
import com.bettetyou.feature.notification.presentation.component.page.RemindersPage
import com.bettetyou.feature.notification.presentation.screen.NotificationEvent.GetNotifications
import com.bettetyou.feature.notification.presentation.screen.NotificationEvent.ObserveWaterReminderStatus
import com.bettetyou.feature.notification.presentation.screen.NotificationEvent.OnBackClick
import com.bettetyou.feature.notification.presentation.screen.NotificationEvent.OnPermissionResultReceived
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.presentation.extensions.CollectSideEffects
import com.example.betteryou.presentation.snackbar.SnackBarController
import com.example.betteryou.presentation.snackbar.SnackbarAction
import com.example.betteryou.presentation.snackbar.SnackbarEvent
import kotlinx.coroutines.launch

@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToDaily: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        viewModel.onEvent(OnPermissionResultReceived(isGranted))
    }

    viewModel.sideEffect.CollectSideEffects { effect ->
        when (effect) {
            is NotificationSideEffect.NavigateBack -> onNavigateBack()

            is NotificationSideEffect.RequestNotificationPermission -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val permission = Manifest.permission.POST_NOTIFICATIONS
                    val isGranted = ContextCompat.checkSelfPermission(
                        context, permission
                    ) == PackageManager.PERMISSION_GRANTED

                    if (isGranted) {
                        viewModel.onEvent(OnPermissionResultReceived(true))
                    } else {
                        permissionLauncher.launch(permission)
                    }
                }
            }

            is NotificationSideEffect.NavigateToDaily -> onNavigateToDaily()

            is NotificationSideEffect.ShowPermissionRequiredMessage -> {
                SnackBarController.sendEvent(
                    SnackbarEvent(
                        message = effect.message.asString(context),
                        action = SnackbarAction(
                            name = context.getString(R.string.settings),
                            action = {
                                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                                    putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                                }
                                context.startActivity(intent)
                            }
                        )
                    )
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(ObserveWaterReminderStatus)
        viewModel.onEvent(GetNotifications)
    }


    NotificationContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NotificationContent(
    state: NotificationState,
    onEvent: (NotificationEvent) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { 2 })
    val scope = rememberCoroutineScope()
    val tabsState = state.tabs

    Scaffold(
        containerColor = TBCTheme.colors.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.notifications),
                        style = TBCTheme.typography.headlineLarge,
                        color = TBCTheme.colors.textPrimary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onEvent.invoke(OnBackClick) }) {
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(TBCTheme.colors.background)
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = TBCTheme.colors.background,
                contentColor = TBCTheme.colors.textPrimary,
                indicator = { tabPositions ->
                    if (pagerState.currentPage < tabPositions.size) {
                        Box(
                            Modifier
                                .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                                .fillMaxWidth()
                                .height(3.dp)
                                .background(
                                    TBCTheme.colors.accent,
                                    RoundedCornerShape(
                                        topStart = 3.dp,
                                        topEnd = 3.dp,
                                        bottomStart = 3.dp,
                                        bottomEnd = 3.dp
                                    )
                                )
                        )
                    }
                },
                divider = {
                    HorizontalDivider(color = TBCTheme.colors.transparentBack)
                }
            ) {
                tabsState.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch { pagerState.animateScrollToPage(index) }
                        },
                        text = {
                            Text(
                                text = stringResource(title),
                                color = TBCTheme.colors.textPrimary,
                                style = TBCTheme.typography.bodyMedium
                            )
                        }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.Top
            ) { page ->
                when (page) {
                    0 -> RemindersPage(state, onEvent)
                    1 -> HistoryPage(state, onEvent)
                }
            }
        }
    }
}





