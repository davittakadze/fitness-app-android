package com.example.betteryou.core_ui.local_theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class TBCAppColors(
    val background: Color,
    val onBackground:Color,
    val surface: Color,
    val primary: Color,
    val onPrimary: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val border: Color,
    val accent: Color,
    val avatarBorder:Color,
    val surfaceVariant: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val outline: Color,
    val transparentBack:Color = Color.Transparent,
    val destructiveColor: Color=Color(0xFFD32F2F)
)

val LightAppColors = TBCAppColors(
    background = Color.White,
    onBackground = Color(0xFF111111),
    surface = Color(0xFFF8F8F8),
    primary = Color(0xFF111111),
    onPrimary = Color.White,
    textPrimary = Color(0xFF111111),
    textSecondary = Color(0xFF6E6E6E),
    border = Color(0xFFE0E0E0),
    accent = Color(0xFF6650a4),
    avatarBorder = Color(0xFFD6D6D6),
    surfaceVariant = Color(0xFFF5F5F5),
    primaryContainer = Color(0xFFEADDFF),
    onPrimaryContainer = Color(0xFF21005D),
    outline = Color(0xFF79747E)
)

val DarkAppColors = TBCAppColors(
    background = Color(0xFF1A2328),
    surface = Color(0xFF243238),
    primary = Color(0xFF2F3E46),
    onPrimary = Color(0xFFF2F6F7),
    onBackground = Color(0xFFF2F6F7),
    textPrimary = Color(0xFFF2F6F7),
    textSecondary = Color(0xFFB6C2C9),
    border = Color(0xFF3A4A52),
    accent = Color(0xFF4FB3BF),
    avatarBorder = Color(0xFF3A3A3A),
    surfaceVariant = Color(0xFF243238),
    primaryContainer = Color(0xFF4F378B),
    onPrimaryContainer = Color(0xFFEADDFF),
    outline = Color(0xFF938F99)
)

val LocalTBCColors = staticCompositionLocalOf<TBCAppColors> {
    error("TBCColors not provided")
}
