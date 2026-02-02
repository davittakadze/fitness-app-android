package com.example.betteryou.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.example.betteryou.ui.theme.local_theme.DarkAppColors
import com.example.betteryou.ui.theme.local_theme.LightAppColors
import com.example.betteryou.ui.theme.local_theme.LocalTBCColors
import com.example.betteryou.ui.theme.local_theme.LocalTBCTypography
import com.example.betteryou.ui.theme.local_theme.TBCAppTypography

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
