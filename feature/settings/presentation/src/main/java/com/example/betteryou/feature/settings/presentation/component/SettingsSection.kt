package com.example.betteryou.feature.settings.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.betteryou.core_ui.theme.LocalTBCColors
import com.example.betteryou.core_ui.theme.Radius

@Composable
fun SettingsSection(
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