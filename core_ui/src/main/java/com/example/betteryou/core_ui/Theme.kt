package com.example.betteryou.core_ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.betteryou.core_ui.local_theme.DarkAppColors
import com.example.betteryou.core_ui.local_theme.LightAppColors
import com.example.betteryou.core_ui.local_theme.LocalTBCColors
import com.example.betteryou.core_ui.local_theme.LocalTBCTypography
import com.example.betteryou.core_ui.local_theme.TBCAppColors
import com.example.betteryou.core_ui.local_theme.TBCAppTypography
import com.example.betteryou.core_ui.local_theme.TBCTypography

@Composable
fun TBCTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkAppColors else LightAppColors

    CompositionLocalProvider(
        LocalTBCColors provides colors,
        LocalTBCTypography provides TBCAppTypography
    ) {
        content()
    }
}

object TBCTheme {
    val colors: TBCAppColors
        @Composable
        get() = LocalTBCColors.current

    val typography: TBCTypography
        @Composable
        get() = LocalTBCTypography.current
}