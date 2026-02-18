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
import com.example.betteryou.core_ui.local_theme.LocalTBCColors
import com.example.betteryou.core_res.R
import com.example.betteryou.core_ui.TBCTheme
import com.example.betteryou.core_ui.local_theme.LocalTBCTypography
import com.example.betteryou.core_ui.util.Radius
import com.example.betteryou.core_ui.util.Spacer
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.betteryou.core_ui.util.components.TBCAppSwitch
import com.example.betteryou.presentation.extensions.CollectSideEffects
import com.example.betteryou.presentation.snackbar.SnackBarController
import com.example.betteryou.presentation.snackbar.SnackbarEvent

@Composable
fun SettingsScreen(
    onProfileClick: () -> Unit,
    onNavigateToMenu: () -> Unit,
    onNavigateToHistory: () -> Unit,
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
        }

        Spacer(Modifier.height(Spacer.spacing32))

        SettingsSection {
            SettingsItem(
                title = stringResource(R.string.history),
                textColor = LocalTBCColors.current.onBackground,
                showArrow = true,
                onClick = { onEvent(SettingsEvent.OnHistoryClick) }
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

@Composable
private fun SettingsItem(
    title: String,
    textColor: Color,
    showArrow: Boolean = true,
    showDivider: Boolean = true,
    leadingIcon: Painter? = null,
    trailingIcon: Painter? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: () -> Unit? = {},
) {

    Column {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            leadingIcon?.let {
                Icon(
                    painter = it,
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier
                        .size(20.dp)
                )
            }

            Spacer(Modifier.width(Spacer.spacing16))

            Text(
                text = title,
                color = textColor,
                style = LocalTBCTypography.current.bodyLargest,
                modifier = Modifier.weight(1f)
            )

            trailingContent?.invoke()

            if (trailingContent == null && trailingIcon != null) {
                Icon(
                    painter = trailingIcon,
                    contentDescription = null,
                    tint = LocalTBCColors.current.textSecondary,
                    modifier = Modifier.size(18.dp)
                )
            }

            if (trailingContent == null && trailingIcon == null && showArrow) {
                Icon(
                    painter = painterResource(R.drawable.right_arrow_svgrepo_com),
                    contentDescription = null,
                    tint = LocalTBCColors.current.textSecondary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        if (showDivider) {
            HorizontalDivider(
                Modifier, 0.5.dp,
                LocalTBCColors.current.primary.copy(alpha = 0.08f)
            )
        }
    }
}


@Composable
private fun SettingsSection(
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = LocalTBCColors.current.background,
                shape = Radius.radius16
            )
            .border(
                width = 1.dp,
                color = LocalTBCColors.current.border,
                shape = Radius.radius16
            )
    ) {
        content()
    }
}


@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    TBCTheme {
        SettingsContent({}, SettingsState())
    }
}