package com.example.betteryou.core_ui.theme

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
    val card:Color,
    val destructiveColor: Color=Color(0xFFD32F2F),
    val macroCircleBackground:Color=Color.LightGray,
    val protein:Color=Color(0xFFFF7043),
    val fat:Color=Color(0xFFFFD54F),
    val carbs:Color=Color(0xFF81D4FA),
)

//val LightAppColors = TBCAppColors(
//    background = Color.White,
//    onBackground = Color(0xFF111111),
//    surface = Color(0xFFF8F8F8),
//    primary = Color(0xFF111111),
//    onPrimary = Color.White,
//    textPrimary = Color(0xFF111111),
//    textSecondary = Color(0xFF6E6E6E),
//    border = Color(0xFFE0E0E0),
//    accent = Color(0xFF6650a4),
//    avatarBorder = Color(0xFFD6D6D6),
//    surfaceVariant = Color(0xFFF5F5F5),
//    primaryContainer = Color(0xFFEADDFF),
//    onPrimaryContainer = Color(0xFF21005D),
//    outline = Color(0xFF79747E),
//    card = Color(0xFFF8F8F8),
//)

val LightAppColors = TBCAppColors(
    background = Color(0xFFF4F7F9),
    onBackground = Color(0xFF0F1417),
    surface = Color(0xFFFFFFFF),
    primary = Color(0xFF1F2A30),
    onPrimary = Color.White,
    textPrimary = Color(0xFF0F1417),
    textSecondary = Color(0xFF5F6B73),
    border = Color(0xFFE3E8EC),
    accent = Color(0xFF00C853),
    avatarBorder = Color(0xFFE3E8EC),
    surfaceVariant = Color(0xFFEFF3F6),
    primaryContainer = Color(0xFFD2F8E5),
    onPrimaryContainer = Color(0xFF003D2E),
    outline = Color(0xFFC5CED4),
    card = Color(0xFFFFFFFF),
)

val DarkAppColors = TBCAppColors(
    background = Color(0xFF0F1417),
    surface = Color(0xFF161D21),
    primary = Color(0xFF1F2A30),
    onPrimary = Color(0xFFFFFFFF),
    onBackground = Color(0xFFE6EDF3),
    textPrimary = Color(0xFFE6EDF3),
    textSecondary = Color(0xFF9FB0B8),
    border = Color(0xFF2C3A40),
    accent = Color(0xFF00E676),
    avatarBorder = Color(0xFF2C3A40),
    surfaceVariant = Color(0xFF1B2429),
    primaryContainer = Color(0xFF003D2E),
    onPrimaryContainer = Color(0xFF7CFFD6),
    outline = Color(0xFF3B4A52),
    card = Color(0xFF161D21),
)

//val DarkAppColors = TBCAppColors(
//    background = Color(0xFF1A2328),
//    surface = Color(0xFF243238),
//    primary = Color(0xFF2F3E46),
//    onPrimary = Color(0xFFF2F6F7),
//    onBackground = Color(0xFFF2F6F7),
//    textPrimary = Color(0xFFF2F6F7),
//    textSecondary = Color(0xFFB6C2C9),
//    border = Color(0xFF3A4A52),
//    accent = Color(0xFF4CAF50),
//    avatarBorder = Color(0xFF3A3A3A),
//    surfaceVariant = Color(0xFF243238),
//    primaryContainer = Color(0xFF4F378B),
//    onPrimaryContainer = Color(0xFFEADDFF),
//    outline = Color(0xFF938F99),
//    card = Color(0xFF243238),
//)

val LocalTBCColors = staticCompositionLocalOf<TBCAppColors> {
    error("TBCColors not provided")
}
