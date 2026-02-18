package com.example.betteryou.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

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