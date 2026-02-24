package com.example.betteryou.feature.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.theme.LocalTBCColors
import com.example.betteryou.core_ui.R
import com.example.betteryou.core_ui.theme.TBCTheme
import com.example.betteryou.core_ui.theme.LocalTBCTypography
import com.example.betteryou.core_ui.theme.Radius
import com.example.betteryou.core_ui.theme.Spacer
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.betteryou.core_ui.components.button.TBCAppSwitch
import com.example.betteryou.feature.settings.presentation.component.SettingsItem
import com.example.betteryou.feature.settings.presentation.component.SettingsSection
import com.example.betteryou.presentation.extensions.CollectSideEffects
import com.example.betteryou.presentation.snackbar.SnackBarController
import com.example.betteryou.presentation.snackbar.SnackbarEvent

@Composable
fun SettingsScreen(
    onProfileClick: () -> Unit,
    onNavigateToMenu: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    SettingsContent(viewModel::onEvent, state)
    viewModel.sideEffect.CollectSideEffects { effect ->
        when (effect) {
            is SettingSideEffects.NavigateToProfile -> {
                onProfileClick()
            }

            is SettingSideEffects.ShowError -> {
                SnackBarController.sendEvent(
                    SnackbarEvent(
                        message = effect.message.asString(context)
                    )
                )
            }

            SettingSideEffects.NavigateToMenu -> onNavigateToMenu()

            SettingSideEffects.NavigateToHistory -> onNavigateToHistory()
            SettingSideEffects.NavigateToFavorites -> onNavigateToFavorites()
            SettingSideEffects.NavigateToNotifications -> onNavigateToNotifications()
        }
    }
}

@Composable
private fun SettingsContent(
    onEvent: (SettingsEvent) -> Unit,
    state: SettingsState,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(LocalTBCColors.current.background)
            .padding(24.dp, 64.dp)
    ) {
        Text(
            text = stringResource(R.string.settings),
            color = LocalTBCColors.current.onBackground,
            style = LocalTBCTypography.current.headlineLarge
        )

        Spacer(Modifier.height(Spacer.spacing32))

        SettingsSection {
            SettingsItem(
                title = stringResource(R.string.profile),
                textColor = LocalTBCColors.current.onBackground,
                leadingIcon = painterResource(R.drawable.profile_svgrepo_com_3),
                onClick = { onEvent(SettingsEvent.OnProfileClick) }
            )
        }

        Spacer(Modifier.height(Spacer.spacing32))

        SettingsSection {
            SettingsItem(
                title = stringResource(R.string.dark_theme),
                textColor = LocalTBCColors.current.onBackground,
                leadingIcon = painterResource(R.drawable.crescent_moon_svgrepo_com),
                trailingContent = {
                    TBCAppSwitch(
                        checked = state.isDarkThemeEnabled,
                        onCheckedChange = {
                            onEvent(SettingsEvent.OnDarkThemeChanged(it))
                        }
                    )
                }
            )
            SettingsItem(
                title =
                    if(state.isGeorgianLanguageEnabled){
                        stringResource(R.string.toggle_language_to_english)
                    }else{
                        stringResource(R.string.toggle_language_to_georgian)
                    },
                textColor = LocalTBCColors.current.onBackground,
                trailingContent = {
                    TBCAppSwitch(
                        checked = state.isGeorgianLanguageEnabled,
                        onCheckedChange = {
                            onEvent(SettingsEvent.OnToggleLanguageClick(it))
                        }
                    )
                }
            )
        }

        Spacer(Modifier.height(Spacer.spacing32))

        SettingsSection {
            SettingsItem(
                title = stringResource(R.string.notifications),
                textColor = LocalTBCColors.current.onBackground,
                showArrow = true,
                onClick = { onEvent(SettingsEvent.OnNotificationsClick) }
            )
            SettingsItem(
                title = stringResource(R.string.history),
                textColor = LocalTBCColors.current.onBackground,
                showArrow = true,
                onClick = { onEvent(SettingsEvent.OnHistoryClick) }
            )
            SettingsItem(
                title= stringResource(R.string.favorites_title),
                textColor = LocalTBCColors.current.onBackground,
                showArrow = true,
                onClick = { onEvent(SettingsEvent.OnFavoritesClick) }
            )
            SettingsItem(
                title = stringResource(R.string.log_out),
                textColor = LocalTBCColors.current.destructiveColor,
                showArrow = false,
                onClick = { onEvent(SettingsEvent.OnLogOutClick) }
            )
            SettingsItem(
                title = stringResource(R.string.delete_account),
                textColor = LocalTBCColors.current.destructiveColor,
                showArrow = false,
                onClick = { onEvent(SettingsEvent.OnDeleteAccountClick) }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    TBCTheme {
        SettingsContent({}, SettingsState())
    }
}